package ling572

import java.io.{PrintWriter, File}
import scala.collection.JavaConverters._
import util.{ConditionalFreqDist, SVMLightReader}

object DriverTrain extends App {

  implicit class NullCoalescent[A](a: A){
    def ??(b: => A) = if(a!=null) a else b
  }

  var trainData:File = null
  var modelFile:File = null
  var minGain:Int = 0
  
  ////////////// argument parsing ///////////////////

  def exit(errorText:String) {
    System.out.println(errorText)
    System.exit(1)
  }

  if (args.length != 3)
   exit("Error: usage DriverTrain train_data model_file min_gain")

  try {
    DriverTrain.this.trainData = new File(args(0))
  } catch {
    case e:Exception => exit("Error: invalid train_data file")
  }

  try {
    DriverTrain.this.modelFile = new File(args(1))
  } catch {
    case e:Exception => exit("Error: invalid model_file file")
  }

  try {
    DriverTrain.this.minGain = args(2).toInt
  } catch {
    case e:Exception => exit("Error: invalid min_gain")
  }

  ////////////// setup ///////////////////
  val sysOut = new PrintWriter(modelFile)
  val instances = SVMLightReader.indexInstances(this.trainData);
  
  val model = new TBLModel()
  model.setInstances(instances)
  model.setMinGain(this.minGain)
  
  ///////////// train /////////////////
  model.buildModel();
  model.printModel(this.modelFile)
}

