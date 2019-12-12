import java.lang.IndexOutOfBoundsException

class CustomArrayList<T> {
    private var size: Int = 0
    private var bufferSize: Int = 32
    private var buffer = arrayOfNulls<Any>(bufferSize)

    operator fun get(idx: Int): T {
        if (idx < 0 || idx >= size) {
            throw IndexOutOfBoundsException()
        }
        return buffer[idx] as T
    }

    private fun growBuffer() {
        buffer = buffer.copyOf(bufferSize * 2)
        bufferSize *= 2
    }

    fun add(value : T) {
        if (bufferSize == size) {
            growBuffer()
        }
        buffer[size++] = value
    }

    fun insert(value: T, idx: Int) {
        if (idx < 0 || idx > size) {
            throw IndexOutOfBoundsException()
        }

        if (bufferSize == size) {
            growBuffer()
        }

        for (j in size + 1 downTo idx) {
            buffer[idx + 1] = buffer[idx]
        }

        buffer[idx] = value
        ++size
    }

    fun delete(idx: Int) {
        if (idx < 0 || idx >= size) {
            throw IndexOutOfBoundsException()
        }

        for (j in idx + 1 until size - 1) {
            buffer[j - 1] = buffer[j]
        }
        --size
    }
}