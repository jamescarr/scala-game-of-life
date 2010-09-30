package GameOfLife
import scala.util.Random

class Game(width:Int, height:Int, cells:Set[(Int,Int)]){
  var previouslyLiveCells = cells
  var currentlyLiveCells = previouslyLiveCells
   
  def getLivingCells : Set[(Int,Int)] = {
    currentlyLiveCells
  }  
  def tick{
    previouslyLiveCells = currentlyLiveCells
    for(x <- 0 until height)
      for(y <- 0 until width){
        val neighbors = numberOfLivingNeighbors((x,y))
        if(neighbors < 2 || neighbors >= 4){
          currentlyLiveCells-= ((x,y))
        }else if(!cellAt(x,y) && neighbors == 3){
          currentlyLiveCells += ((x,y))  
        }
      }
  }
  
  def cellAt(x:Int, y:Int):Boolean ={
    currentlyLiveCells.contains((x,y))
  }
  
  private def oldCellAt(x:Int, y:Int):Boolean ={
    previouslyLiveCells.contains((x,y))
  }
  
  private def numberOfLivingNeighbors(cell:(Int,Int)) = {
    List(
      oldCellAt(cell._1-1, cell._2-1),
      oldCellAt(cell._1-1, cell._2),
      oldCellAt(cell._1-1, cell._2+1),
      oldCellAt(cell._1, cell._2-1),
      oldCellAt(cell._1, cell._2+1),
      oldCellAt(cell._1+1, cell._2-1),
      oldCellAt(cell._1+1, cell._2),
      oldCellAt(cell._1+1, cell._2+1)
      ).filter{ cell => cell}.size                                          
  }
}
