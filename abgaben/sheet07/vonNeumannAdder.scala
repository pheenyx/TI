/**
 * 2013-12-11
 *
 * (One more example from the series "How Andrey killed his time")
 *
 * This little demo is supposed to demonstrate how a 
 * von-Neumann adder works.
 *
 * And it's in the same time one of the most ridiculously 
 * complicated ways to add two numbers in any higher language...
 */


type Reg = Array[Boolean]
def add(x: Int, y: Int, registerSize: Int = 30): Int = {
  // convert `x` and `y` into bits
  val acc = toBits(x, registerSize)
  val carry = toBits(y, registerSize)
  var carryBit = false
  var moreIterationsNeeded = true

  def rec(acc: Reg, carry: Reg, outCarry: Boolean): (Reg, Boolean) = {
    print("Acc:   "); showReg(acc)
    print("Carry: "); showReg(carry)
    println("Highest bit: " + outCarry)
    val (newAcc, newCarryUnshifted) = 
      ((acc zip carry) map { case (x, y) => halfAdder(x, y)}) unzip
    val newOutCarry = outCarry | newCarryUnshifted.last
    val newCarry: Reg = 
      (false +: newCarryUnshifted.take(registerSize - 1)).toArray
    if (newCarry.reduce(_ | _)) {
      rec(newAcc.toArray, newCarry, newOutCarry)
    } else {
      (newAcc.toArray, newOutCarry)
    }
  }
  
  val (res, highOrderBit) = rec(acc, carry, false)
  fromBits(res, highOrderBit) 
}

def toBits(x: Int, registerSize: Int): Reg = {
  Array.iterate((x, 0), registerSize + 1){
    case (a, r) => (a / 2, a % 2)
  }.map{_._2 == 1}.tail
}

def showReg(reg: Reg) = println(reg.map{if (_) 1 else 0}.mkString)

def fromBits(register: Reg, highOrderBit: Boolean): Int = {
  register.map{if (_) 1 else 0}.zip(Stream.iterate(1){_ * 2}).map{ case (x, y) => x * y}.sum +
  (if (highOrderBit) 1 << register.size else 0)
}

def halfAdder(x: Boolean, y: Boolean): (Boolean, Boolean) = {
  (x ^ y, x & y)
}

val a = args(0).toInt
val b = args(1).toInt
val registerSize = args(2).toInt
println(add(a, b, registerSize))

// Example:
// 
// scala vonNeumannAdder.scala 175 225 8
// Acc:   11110101
// Carry: 10000111
// Highest bit: false
// Acc:   01110010
// Carry: 01000010
// Highest bit: true
// Acc:   00110000
// Carry: 00100001
// Highest bit: true
// Acc:   00010001
// Carry: 00010000
// Highest bit: true
// Acc:   00000001
// Carry: 00001000
// Highest bit: true
// 400
