import org.specs._;

class GameOfLifeSpecification extends Specification{
  "Initial State" should {
     val game = new GameOfLife(
       List(
         List(false, false, false),
         List(false, true, false),
         List(false, false, false)
     ))
    "be able to access cells by index" in {
      game.cellAt(1,1) must_==true
      game.cellAt(0,0) must_==false
    }
  }
  "Game of Life Evolver" should {
    "cell should die if no neighbors" in {
      val game = new GameOfLife(
       List(
         List(false, false, false),
         List(false, true, false),
         List(false, false, false)
      ))
    
      game.executeTurn()
    
      game.cellAt(1,1) must_==false   
      game.cellAt(0,1) must_==false   
      game.cellAt(2,2) must_==false   
    }
    "cell with one neighbor dies" in {
      println ("cell with one neighbor")
      val game = new GameOfLife(
       List(
         List(false, false, false),
         List(false, true, true),
         List(false, false, false)
      ))
    
      game.executeTurn()
      
      game.cellAt(1,1) must_==false   
      game.cellAt(1,2) must_==false   
      game.cellAt(2,2) must_==false 
    }
    "cell with two neighbors lives" in {
      val game = new GameOfLife(
       List(
         List(false, true, false),
         List(false, true, false),
         List(true, false, false)
      ))
    
      game.executeTurn()
    
      game.cellAt(1,1) must_==true 
      game.cellAt(0,1) must_==false   
      game.cellAt(2,0) must_==false   
    }
    "cell with three neighbors comes to life" in {
      val game = new GameOfLife(
       List(
         List(false, true, false),
         List(false, false, false),
         List(true, false, true)
      ))
    
      game.executeTurn()
      
      game.cellAt(1,1) must_==true
    }
    "dead cell with two neighbors stays dead" in {
      val game = new GameOfLife(
       List(
         List(false, true, false),
         List(false, false, false),
         List(true, false, false)
      ))
    
      game.executeTurn()
      
      game.cellAt(1,1) must_==false
    }

    "cell with 4 neighbors dies by over populaition" in {
      val game = new GameOfLife(
       List(
         List(false, true, true),
         List(false, true, false),
         List(true, false, true)
      ))
      
      game.executeTurn()
      
      game.cellAt(1,1) must_== false
    }
  }

  "Cell" can {
    val cell = new Cell(false, (1,2))  
    "knows it's state" in {
      cell.state must_==false
    }
    "contains it's x and y coords" in {
      cell.x must_==1
      cell.y must_==2
    } 
  }

  "CellManager" should {
    val manager = new CellManager(CreateCells(
       List(
         List(false, false, false),
         List(false, true, true),
         List(false, false, true)
    )))
    "access cell specified by xy coordinates" in {
      manager.cellAt(2,2).state must_==true  
    }
    "get neighbors for bottom corner cell" in {
      val c = manager.cellAt(_,_)
      val cell = c(2,2)
      
      val neighbors = manager.getNeighborsFor(cell)
      
      neighbors must have size(3)
      neighbors must_==List(c(1,1), c(1,2), c(2,1))
    }
    "get neighbors for corner cell" in {
      val c = manager.cellAt(_,_)
      val cell = c(0,0)
      
      val neighbors = manager.getNeighborsFor(cell)
      
      neighbors must have size(3)
      neighbors must_==List(c(0,1), c(1,0), c(1,1))
    }
    "get neighbors for center cell" in {
      val c = manager.cellAt(_,_)
      val cell = c(1,1)
      
      val neighbors = manager.getNeighborsFor(cell)
      
      neighbors must have size(8)
      neighbors must_==List(
        c(0,0), c(0,1), c(0,2),
        c(1,0), c(1,2),
        c(2,0), c(2,1), c(2,2)
      )
    }
    "report how many live nieghbors a cell has" in {
      val neighbors = manager.numberOfLiveNeighbors(manager.cellAt(0,1))
    
      neighbors must_==2
    }
    "execute a function on each cell in the grid" in {
      val newManager = manager.transform{c:Cell =>  new Cell(!c.state, (0,0))}
      
      newManager.cellAt(0,0).state must_==true
      newManager.cellAt(2,2).state must_==false
    }
  }
}


