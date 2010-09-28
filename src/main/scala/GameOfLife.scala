import scala.collection.mutable.ListBuffer

class GameOfLife(val grid:List[List[Boolean]]) {
  private val previousGrid = new CellManager(CreateCells(grid))
  private var currentGrid = previousGrid
  
  def cellAt(x:Int, y:Int)={
    currentGrid.cellAt(x,y).state
  }
  
  def executeTurn(){
    currentGrid = previousGrid.transform({cell =>
      val alive = previousGrid.numberOfLiveNeighbors(cell)
      if(!cell.state)
        new Cell(alive == 3, (cell.x, cell.y))
      else
        new Cell(alive > 1 && alive < 4, (cell.x, cell.y))
    })
  }
}

class Cell(val state:Boolean, val coords:(Int, Int)){
  val x = coords._1
  val y = coords._2
}
object CreateCells extends (List[List[Boolean]] => List[List[Cell]]){
  override def apply(list:List[List[Boolean]] ) ={
    list.zipWithIndex.map{row=>
      row._1.zipWithIndex.map{cell=>
        new Cell(cell._1, (row._2, cell._2))
      }
    }
  }
}
class CellManager(val cells: List[List[Cell]]){
  def cellAt(x:Int, y:Int) = {
    if (x == -1 || y == -1 || x >= cells.size || y >= cells(x).size)
      new Cell(false, (-1,-1))
    else
      cells(x)(y)
  }
  
  def numberOfLiveNeighbors(cell:Cell) = {
    getNeighborsFor(cell).filter(_.state).size
  }
  def getNeighborsFor(cell:Cell) = {
    List(
      cellAt(cell.x-1, cell.y-1),
      cellAt(cell.x-1, cell.y),
      cellAt(cell.x-1, cell.y+1),
      cellAt(cell.x, cell.y-1),
      cellAt(cell.x, cell.y+1),
      cellAt(cell.x+1, cell.y-1),
      cellAt(cell.x+1, cell.y),
      cellAt(cell.x+1, cell.y+1)
    ).filter(_.x != -1)
  }
  def transform(fn:Cell => Cell) = {
    new CellManager(
      cells.map{
        _.map(fn)
      }
    )
  }
}
