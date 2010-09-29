/*
 * ScalaTetrix.scala
 */

package GameOfLife

import swing._
import event._
import scala.util.Random

object App extends SimpleSwingApplication {
  import event.Key._
  import java.awt.{Dimension, Graphics2D, Graphics, Image, Rectangle}
  import java.awt.{Color => AWTColor}
  import java.awt.event.{ActionEvent}
  import javax.swing.{Timer => SwingTimer, AbstractAction}

  val random = new Random()
  override def top = frame
  val frame = new MainFrame {
    title = "Conway's Game of Life"
    contents = mainPanel
    lazy val mainPanel = new Panel() {
      focusable = true
      background = AWTColor.white
      preferredSize = new Dimension(600, 600)

      override def paint(g: Graphics2D) {
        g.setColor(AWTColor.white)
        g.fillRect(0, 0, size.width, size.height)
        onPaint(g)
      }
    } // new Panel()

  def onPaint(g: Graphics2D) {
    val CELL_SIZE: Int = 20
    val CELL_MARGIN: Int = 1
    val darkRed = new AWTColor(200, 100, 100)

    def buildRect(p: Tuple2[Int, Int], board: Board): Rectangle =
      new Rectangle(p._1 * (CELL_SIZE + CELL_MARGIN) + board.pos._1,
        (board.size._2 - p._2 - 1) * (CELL_SIZE + CELL_MARGIN) + board.pos._2,
        CELL_SIZE,
        CELL_SIZE)

    def drawBoard(board: Board) {
      g.setColor(AWTColor.gray)

      board.coordinates.
        foreach(p => g draw buildRect(p, board))

      board.cells.foreach(g fill buildRect(_, board))
    }

    drawBoard(board)
  }
  var board = new Board((25, 25), (25, 25))
  var counter = 0
    val timer = new SwingTimer(1000, new AbstractAction() {
      override def actionPerformed(e: ActionEvent) {
        //board += ((counter, counter))   
        //counter+=1
        board = board.randomFill(random.nextInt(25))
        repaint()
      }
    })
  timer.start
  } // def top new MainFrame
} // object App

class Board(
  val size: Tuple2[Int, Int],
  val pos: Tuple2[Int, Int],
  val cells: Set[Tuple2[Int, Int]]
) {
  def this(size: Tuple2[Int, Int],pos: Tuple2[Int, Int]) = {
    this(size, pos, Set.empty)
  }
  def coordinates =
    for (y <- 0 until size._2; x <- 0 until size._1)
      yield(x, y)
  
  def +(cell:Tuple2[Int,Int]) = new Board(size, pos, cells+cell)

  def randomFill(n:Int) = {
    val random = new Random
    var cells:Set[Tuple2[Int,Int]] = Set.empty
    for(x <- 0 until n)
      cells += ((random.nextInt(size._1), random.nextInt(size._2)))
    new Board(size, pos, cells)
  }
  def clear() =
    new Board(size, pos)
}

