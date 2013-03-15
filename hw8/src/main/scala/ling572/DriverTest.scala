package ling572

import java.io.{PrintWriter, File}
import scala.collection.JavaConverters._
import util.{ConditionalFreqDist, SVMLightReader, MapInstance}

object DriverTest extends App {

  implicit class NullCoalescent[A](a: A){
    def ??(b: => A) = if(a!=null) a else b
  }

  var testData:File = null
  var modelFile:File = null
  var outputFile:File = null
  var n:Int = 0;

  ////////////// argument parsing ///////////////////
  def exit(errorText:String) {
    System.out.println(errorText)
    System.exit(1)
  }

  if (args.length != 4)
   exit("Error: usage DriverTest test_data model_file sys_output n")

  try {
    DriverTest.this.testData = new File(args(0))
  } catch {
    case e:Exception => exit("Error: invalid test_data file")
  }

  try {
    DriverTest.this.modelFile = new File(args(1))
  } catch {
    case e:Exception => exit("Error: invalid model_data file")
  }
  
  try {
    DriverTest.this.outputFile = new File(args(2))
  } catch {
    case e:Exception => exit("Error: invalid sys_output file")
  }
    
  try {
    DriverTest.this.n = args(3).toInt
  } catch {
    case e:Exception => exit("Error: invalid n")
  }

  ////////////// setup ///////////////////
  val sysOut = new PrintWriter(outputFile)
  val testInstances = SVMLightReader.indexInstances(this.testData);
  
  val model = new TBLModel();
  model.loadModel(this.modelFile)
  
  val confusionMatrix = new ConditionalFreqDist[String]()

  ///////////// process /////////////////
  var count = 0
  var correct = 0
  testInstances.asScala.foreach { case (instance: MapInstance[Integer]) =>
     val transformations = model.processInstance(instance,this.n)

     var classLabel:String = null
     var transCnt = transformations.size()
     
     if (transCnt==0)
    	 classLabel = model.getDefaultLabel()
     else
    	 classLabel = transformations.get(transformations.size()-1).getToClass()
     
     if (classLabel.toString.equals(instance.getLabel)) correct += 1
     confusionMatrix.add(instance.getLabel, classLabel.toString)
     
     sysOut.print(count + " " + instance.getLabel() + " " + classLabel)
     transformations.asScala.foreach { case (trans:Transformation) =>
       sysOut.print(" " + trans.getFeatName() + " " + trans.getFromClass() + " " + trans.getToClass())
     }
     sysOut.println();
     count += 1
  }
  sysOut.close()

  // print confusion matrix
  val classLabels = confusionMatrix.keySet.toSeq.sorted

  print("\t")
  for (label <- classLabels) yield print(label + " ")
  println()
  for (gold <- classLabels) yield {
    print(gold + " ")
    for (label <- classLabels) yield {
      print(confusionMatrix.N(gold, label) + " ")
    }
    println()
  }

  println(" Test accuracy=" + (correct.toDouble) / count.toDouble)
}