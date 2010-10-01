import org.specs._
import GameOfLife.Game

object GameOfLifeSpec extends Specification{
  "Initialized Board" should{
    "contain all living cells initialized with" in {
      val game = new Game(10,10, Set((1,1),(1,2)))

      game.getLivingCells must_== Set((1,1), (1,2))
    }
  }

  "Given a cell with fewer than two neighbors it " should{
    val game = new Game(10,10, Set((2,0), (1,2), (0,2)))
    
    game.tick
    "die if no neighbors" in {
      game.getLivingCells must not contain((2,0))
    }

    "die if it has only one neighbor" in {
      game.getLivingCells must not contain((0,2))
      game.getLivingCells must not contain((1,2))
    }
  }
  "Cell with more than one neighbor" should {
    "live on to the next round if it has two neighbors above" in {
      val game = new Game(10,10, Set((0,0), (1,1), (0,2)))
      
      game.tick
      
      game.getLivingCells must contain( (1,1) )
    }
    "live on to the next round if it has two neighbors below" in {
      val game = new Game(10,10, Set((1,0), (0,1), (1,2)))
      
      game.tick
      
      game.getLivingCells must contain( (0,1) )
    }
    
    "live on with neighbors above and below" in {
      val game = new Game(10,10, Set((0,1), (1,1), (2,1)))
      
      game.tick
      
      game.getLivingCells must contain ((1,1))
    }
    "live on with neighbors on either side " in {
      val game = new Game(10,10, Set((3,5), (3,6), (3,7)))
      
      game.tick
      
      game.getLivingCells must contain ((3,6))
    }
  }
  
  "Cell with more than three neighbors" should{
    "die by overpopulation" in {
      val game = new Game(10,10, Set((0,0), (0,2), (1,1), (2,0), (2,2)))
      
      game.tick
      
      game.getLivingCells must not contain ((1,1)) 
    }
  }
  
  "Dead cell with three neighbors" should {
    val game = new Game(3, 3, Set( (0,0), (0,2), (2,2)))
    
    game.tick
    
    "come to life" in {
      game.getLivingCells must contain( (1,1) )  
    }
  }
} 
