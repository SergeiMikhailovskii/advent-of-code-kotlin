val suitableDirs = mutableListOf<NodeWithSize>()
var needSpace = 0

fun main() {
    val inputList = readInput("Day07_test").filter { it != "\$ ls" && it != "\$ cd /" }
    val root = Node("/")
    var currentDir: Node? = root

    inputList.forEach {
        if (it.contains("dir")) {
            val dirName = it.split(" ")[1]
            val dir = Node(name = dirName)
            currentDir?.addChild(dir)
        } else if (it.contains("\$ cd")) {
            val param = it.split(" ")[2]
            if (param == "..") {
                currentDir = currentDir?.parent
            } else {
                currentDir = currentDir?.children?.first { it.name == param }
            }
        } else {
            val size = it.split(" ")[0].toInt()
            val name = it.split(" ")[1]
            currentDir?.addChild(Leaf(size, name))
        }
    }

    needSpace = getNodeSize(root) - 40_000_000
    suitableDirs.clear()

    findAvailableDirs(root)

    println(suitableDirs.minBy { it.size })
}

open class Node(
    val name: String,
    val children: MutableList<Node>? = mutableListOf(),
    var parent: Node? = null,
) {
    fun addChild(node: Node): Node {
        node.parent = this
        children?.add(node)
        return node
    }
}

fun getNodeSize(node: Node): Int {
    var size = 0
    node.children?.forEach {
        size += if (it is Leaf) {
            it.size
        } else {
            getNodeSize(it)
        }
    }
    if (size > needSpace) {
        suitableDirs.add(NodeWithSize(node, size))
    }
    return size
}

fun findAvailableDirs(node: Node) {
    var size = 0
    node.children?.forEach {
        size += if (it is Leaf) {
            it.size
        } else {
            getNodeSize(it)
        }
    }
}

class Leaf(
    val size: Int,
    name: String
) : Node(children = null, name = name)

data class NodeWithSize(
    val node: Node,
    val size: Int
)