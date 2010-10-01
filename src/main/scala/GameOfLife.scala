package GameOfLife

class Game(width:Int, height:Int, cells:Set[(Int,Int)]){
  private var previousLiveCells = cells
  private var currentLiveCells = previousLiveCells
  def getLivingCells : Set[(Int,Int)] = {
    currentLiveCells
  }  
  def tick{
    previousLiveCells = currentLiveCells
    for(x <- 0 until height) 
      for(y <- 0 until width){
        val cell = (x,y)
        val num = numberOfNeighbors(cell )
        if(previousLiveCells.contains(cell) && (num < 2 || num > 3))
          currentLiveCells -= cell
        else if (!previousLiveCells.contains(cell) && (num == 3))
          currentLiveCells += cell
          
      }
  }
  
  private def numberOfNeighbors(cell:(Int,Int)) = {
    previousLiveCells.count {  aCell =>
      aCell == (cell._1-1, cell._2-1 )  || 
      aCell == (cell._1-1, cell._2+1 )  ||
      aCell == (cell._1+1, cell._2-1 )  ||
      aCell == (cell._1+1, cell._2+1 )  ||
      aCell == (cell._1-1, cell._2 )  ||
      aCell == (cell._1+1, cell._2 )  ||
      aCell == (cell._1, cell._2-1 )  ||
      aCell == (cell._1, cell._2+1 )  
    }
  }
}
