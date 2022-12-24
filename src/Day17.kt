fun main() {
    val input = readInput("Day17_test")
    fun shape(si: Int, u: (Int, Int) -> Unit) {
        when (si) {
            0 -> {
                u(0, 0)
                u(0, 1)
                u(0, 2)
                u(0, 3)
            }
            1 -> {
                u(0, 1)
                u(1, 0)
                u(1, 1)
                u(1, 2)
                u(2, 1)
            }
            2 -> {
                u(0, 0)
                u(0, 1)
                u(0, 2)
                u(1, 2)
                u(2, 2)
            }
            3 -> {
                u(0, 0)
                u(1, 0)
                u(2, 0)
                u(3, 0)
            }
            4 -> {
                u(0, 0)
                u(0, 1)
                u(1, 0)
                u(1, 1)
            }
            else -> error(si)
        }
    }
    val f = ArrayList<BooleanArray>()
    fun test(si: Int, i0: Int, j0: Int): Boolean {
        if (i0 < 0) return false
        var ok = true
        shape(si) { di, dj ->
            val i = i0 + di
            val j = j0 + dj
            if (j !in 0..6) {
                ok = false
            } else {
                if (i in f.indices && f[i][j]) ok = false
            }
        }
        return ok
    }
    fun put(si: Int, i0: Int, j0: Int) {
        shape(si) { di, dj ->
            val i = i0 + di
            val j = j0 + dj
            while (i > f.lastIndex) f += BooleanArray(7)
            f[i][j] = true
        }
    }
    val p = input[0]
    var pj = 0
    val sn = 5
    var si = sn - 1
    data class Upd(val si: Int, val pj: Int, val ds: Int, val i: Int, val j: Int) // ds - насколько изменилась высота после падения фигуры
    val us = ArrayList<Upd>()
    val ui = HashMap<Upd, Int>()
    var ans = 0L
    val times = 1000000000000L
    fun rec(u: Upd): Boolean {
        val rn = us.size
        val prev = ui[u]
        if (prev == null) {
            ui[u] = rn
            us += u
            return false
        }
        val clen = rn - prev
        val rem = times - rn
        var ds0 = 0
        val iRest = prev + (rem % clen).toInt()
        for (k in 0 until rn) ds0 += us[k].ds
        for (k in prev until iRest) ds0 += us[k].ds
        var dsCycle = 0
        for (k in prev until rn) dsCycle += us[k].ds
        ans = ds0 + (rem / clen) * dsCycle // ds0 - высота до цикла, rem - количество элементов оставшихся, clen - длина цикла, dsCycle - изменение высоты за цикл
        return true
    }
    for (rn in 0 until times) {
        si = (si + 1) % sn // текущая фигура. sn - всего фигур (всегда 5)
        val s0 = f.size // f - поле. каждый j - true/false
        var i = s0 + 3 // старт i
        var j = 2
        while (true) {
            val d = p[pj] // текущая команда. тк список команд может быть меньше чем количество команд, то зацикливаем его
            pj = (pj + 1) % p.length // следующая команда
            val dj = when (d) {
                '<' -> -1
                '>' -> 1
                else -> error(d)
            }
            if (test(si, i, j + dj)) j += dj // если можем сдвинуть вправо/влево - двигаем
            if (!test(si, i - 1, j)) break // если можем сдвинуть вниз - двигаем. нет - сохраняем и переходим к следующей фигуре, если не отловили цикл
            i--
        }
        put(si, i, j) // сохраняем финальную позицию в поле
        if (rec(Upd(si, pj, f.size - s0, s0 - i, j))) break // если нашли цикл - считаем результат
    }
    println(ans)
}