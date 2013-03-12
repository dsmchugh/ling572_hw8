package ling572.svm

import ling572.util.VectorInstance
import cern.colt.matrix.tdouble.DoubleMatrix1D
import scala.collection.JavaConverters._
import ling572.KernelType

class SVMModel(val gamma:Double = 1.0, val coef0:Double = 0.0, val degree:Double = 1.0, val rho:Double = 0.0,
               var kernelType:KernelType = KernelType.linear) {

  type KernelMethod = (DoubleMatrix1D, DoubleMatrix1D) => Double

  //////////////////////////////
  // KERNELS

  var supportVectors: List[VectorInstance] = null

  val linear:KernelMethod = (u:DoubleMatrix1D, v:DoubleMatrix1D) => u.zDotProduct(v)

  val polynomial:KernelMethod = (u:DoubleMatrix1D, v:DoubleMatrix1D) => {
    math.pow(gamma * u.zDotProduct(v) + coef0, degree)
  }

  val rbf:KernelMethod = (u:DoubleMatrix1D, v:DoubleMatrix1D) => {
    // in testing this was ~30% faster than a direct Euclidean.
    val sq_dist = u.zDotProduct(u) + v.zDotProduct(v) - 2*u.zDotProduct(v)
    math.exp(-gamma * sq_dist)
  }

  val sigmoid:KernelMethod = (u:DoubleMatrix1D, v:DoubleMatrix1D) => {
    math.tanh(gamma * u.zDotProduct(v) + coef0)
  }


  ///////////////////////////////
  // SUPPORT

  def setSupportVectors(vectors:List[VectorInstance]) {
    supportVectors = vectors
  }

  def setSupportVectors(vectors:java.util.List[VectorInstance]) {
    supportVectors = vectors.asScala.toList
  }

  /////////////////////////////////
  // SCORING

  // NB: the parallel add here adds a non-deterministic FP error, but it
  // doesn't matter (it'll always be on the correct side of zero)
  def scoreInstance(u:VectorInstance, kernel:KernelMethod):Double = {
    val uvec = u.getVector
    supportVectors.par.map(v => v.getWeight * kernel(uvec,v.getVector)).sum - rho
  }

  def classifyInstance(instance:VectorInstance):(Int,Double) = {
    val kernel =  kernelType match {
      case KernelType.linear => linear
      case KernelType.polynomial => polynomial
      case KernelType.rbf => rbf
      case KernelType.sigmoid => sigmoid
    }
    val score = scoreInstance(instance, kernel)
    ((if (score >= 0) 0 else 1), score)
  }


}
