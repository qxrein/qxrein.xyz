import org.scalajs.dom
import org.scalajs.dom.{document, window, Element, HTMLDivElement, HTMLElement}
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportTopLevel, JSExport}

object MatrixButtons {
  val buttonLabels = Array("GITHUB", "ABOUT", "BLOG", "CONTACT")
  val buttonLinks = Array(
    "https://github.com/qxrein", // HOME now redirects to GitHub
    "https://about.qxrein.xyz",  // ABOUT
    "https://blog.qxrein.xyz",   // BLOG
    "https://contact.qxrein.xyz" // CONTACT
  )
  val buttonColors = Array(
    "rgba(128, 0, 128, 0.3)",  // Very translucent dark purple
    "rgba(139, 0, 0, 0.3)",    // Very translucent dark red
    "rgba(165, 42, 42, 0.3)",  // Very translucent light red
    "rgba(255, 140, 0, 0.3)"   // Very translucent dark orange
  )
  
  // Track window size to calculate button dimensions
  var windowWidth: Int = 0
  var windowHeight: Int = 0
  
  def main(args: Array[String]): Unit = {
    // Make sure the DOM is loaded before we start
    document.addEventListener("DOMContentLoaded", { (_: dom.Event) =>
      setupMatrix()
    })
  }
  
  def setupMatrix(): Unit = {
    val container = document.getElementById("matrix-container")
    
    // Update window dimensions
    updateWindowDimensions()
    
    // Create the exact number of buttons to fill the viewport
    fillViewport(container)
    
    // Handle window resizing
    window.addEventListener("resize", { (_: dom.Event) =>
      updateWindowDimensions()
      
      // Clear existing buttons
      while (container.firstChild != null) {
        container.removeChild(container.firstChild)
      }
      
      // Refill the viewport
      fillViewport(container)
    })
  }
  
  def updateWindowDimensions(): Unit = {
    windowWidth = window.innerWidth.toInt
    windowHeight = window.innerHeight.toInt
  }
  
  def fillViewport(container: Element): Unit = {
    // Calculate how many rows we need to fill the viewport exactly
    val rowHeight = 80 // Approximate height of each row including margin
    val rowsNeeded = Math.ceil(windowHeight / rowHeight).toInt
    
    // Add the calculated number of rows
    for (_ <- 0 until rowsNeeded) {
      addMatrixRow(container)
    }
  }
  
  def addMatrixRow(container: Element): Unit = {
    val row = document.createElement("div").asInstanceOf[HTMLDivElement]
    row.setAttribute("class", "matrix-row")
    
    // Calculate optimal button distribution
    val buttonWidth = 150 // Average width of a button including margins
    val buttonsPerRow = Math.max(4, windowWidth / buttonWidth) // At least 4 buttons per row
    
    // Create buttons to fill the row
    for (_ <- 0 until buttonsPerRow) {
      addButton(row)
    }
    
    container.appendChild(row)
  }
  
  def addButton(row: Element): Unit = {
    val button = document.createElement("div").asInstanceOf[HTMLDivElement]
    button.setAttribute("class", "matrix-button")
    
    // Randomly pick a label and color
    val labelIndex = scala.util.Random.nextInt(buttonLabels.length)
    val colorIndex = scala.util.Random.nextInt(buttonColors.length)
    
    button.textContent = buttonLabels(labelIndex)
    // The color is set in CSS with !important to make it more translucent
    button.style.backgroundColor = buttonColors(colorIndex)
    
    // Add a random width to make it more interesting
    val minWidth = 100
    val maxWidth = 200
    val width = minWidth + scala.util.Random.nextInt(maxWidth - minWidth + 1)
    button.style.width = s"${width}px"
    
    // Add click event to redirect to corresponding link
    button.addEventListener("click", { (_: dom.Event) =>
      // Get the current label and find its index to determine the link
      val currentLabel = button.textContent
      val linkIndex = buttonLabels.indexOf(currentLabel)
      if (linkIndex >= 0) {
        // Open the link in a new tab
        window.open(buttonLinks(linkIndex), "_blank")
      }
    })
    
    row.appendChild(button)
  }
}
