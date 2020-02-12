package gmarsh.csci448.com.geoquiz

class Question() {
    var textResId = 0
    var isAnswerTrue = ""
    var questionType= ""

    var ans1 = ""
    var ans2 = ""
    var ans3 = ""
    var ans4 = ""

    constructor(id: Int, answer: String, type: String): this() {
        textResId = id
        isAnswerTrue = answer
        questionType = type
    }

    constructor(id: Int, answer: String, type: String, a1: String, a2: String, a3: String, a4: String): this() {
        textResId = id
        isAnswerTrue = answer
        questionType = type

        ans1 = a1
        ans2 = a2
        ans3 = a3
        ans4 = a4
    }
}

