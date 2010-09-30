import org.specs._
import GameOfLife.Game

object GameOfLifeSpec extends Specification{
  "Initialized Game" should {
     val game = new Game(3,3, Set((1,1), (2,2)))
    "have cell specified by tuple be initialized to alive" in {
      game.cellAt(1,1) must_== true
      game.cellAt(2,2) must_== true
    }
    "have cells not in tuple set as dead" in {
      game.cellAt(0,0) must_== false
      game.cellAt(0,1) must_== false
      game.cellAt(0,2) must_== false
      game.cellAt(1,0) must_== false
      game.cellAt(1,2) must_== false
      game.cellAt(2,0) must_== false            
      game.cellAt(2,1) must_== false            
    }
  }
  "When cell has one or fewer neighbors it" should {
    "die in the next round with no nieghbors" in {
      val game = new Game(3, 3, Set((1,1)))
      
      game.tick
      
      game.cellAt(1,1) must_== false
    }
  }
  
  "Cases when a cell should live on" should{
    "continue if it has two neighbors" in {
      val game = new Game(3, 3, Set((1,1), (2,2), (0,1)))
      
      game.tick
      
      game.getLivingCells must_== Set((1,1), (1,2))
    }
    "all cells live on if they all have two neighbors" in {
      val game = new Game(3, 3, Set((0,0), (0,1), (1,0)))
      
      game.tick
      
      game.getLivingCells must_== Set((0,0), (0,1), (1,0), (1,1))
    }
    "continue living if it has 3 neighbors" in {
      val game = new Game(3, 3, Set((0,0), (0,2), (1,1), (2,2)))
      
      game.tick
      
      game.getLivingCells must_== Set((1,1), (0,1), (1,2))
    }
  }
  "Cell with too many neighbors" should{
    "die if it has four neighbors" in {
      val game = new Game(3, 3, Set((0,0), (0,2), (1,0), (1,1), (2,2)))
      
      game.tick
      
      game.getLivingCells must_== Set((0,0), (1,0), (2,1), (1,2))
    
    }
    "die if it has five neighbors" in {
      val game = new Game(3, 3, Set((0,0), (0,2), (1,0), (1,1), (1,2), (2,2)))
      
      game.tick
      
      game.getLivingCells must_== Set((0,0), (0,2), (1,0), (1,2), (2,2))
    }
  }
  "When a dead cell has three neighbors it" should {
    "come to life" in {
      val game = new Game(3, 3, Set((0,0), (0,2), (2,2)))
      
      game.tick
      
      game.getLivingCells must_== Set((1,1))
    }
  }
  
  "Running multiple rounds" should {
    "mutate the board each time based on current board" in {
      val game = new Game(3, 3, Set((0,1), (1,1), (2,1)))
      
      game.tick
      
      game.getLivingCells must_== Set((1,0),(1,1),(1,2))
      
      game.tick
      
      game.getLivingCells must_== Set((0,1),(1,1),(2,1))
    }
  }
} 
