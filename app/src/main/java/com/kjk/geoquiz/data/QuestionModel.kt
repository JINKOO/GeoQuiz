package com.kjk.geoquiz.data

import android.util.Log
import com.kjk.geoquiz.MyInterface
/**
 *  비즈니스 로직을 담당하는 부분.
 *  Model 컴포넌트에 속한다.
 *  Context속성이 포함되면 안된다.
 */


/**
 * MVC Pattern에서 비즈니스 로직을 포함하는 Model은,
 * Context속성이 포함되면 안된다. 순수 자바 코드로만 이루어 져야 한다.
 *
 * 만약, 비즈니스 로직을 처리하는 부분에서 R.string값을 가져오려면 어떡해 해야할까?
 * 아래와 같이 interface를 만들고,
 * Activity에서 model로 interface를 넘긴다. 정확히 말하면 구현체를 넘긴다.
 */
//interface SampleInterface {
//    fun getStringTest():String
//    fun abcd()
//}
//
//class SampleActivity : SampleInterface {
//
//    val model =  Model()
//    val SDFFD: Int = 0
//
//    fun oncreate(){
//        // 보통 this로 파라미터를 넘겨준다.
//        model.setInterface(this)
//    }
//
//    override fun getStringTest(): String {
////        return context.getstring(R.id.dfsdfs)
//        return ""
//    }
//
//    override fun abcd() {
//
//    }
//}
//
//
//class Model {
//
//    var mInterface : SampleInterface? = null
//
//    /** 여기서 파라미터는 Activity가 넘어온다. Activity가 interface를 구현했으므*/
//    fun setInterface(mInterface :SampleInterface){
//        this.mInterface = mInterface
//    }
//
//    fun getString(){
//        val sdfsdf = mInterface?.getStringTest()
//        Log.w(sdfsdf, "안녕하세요")
//    }
//
//}

class QuestionModel {

    private var questionList = ArrayList<QuestionEntity>()

    // interface 변수를 활용한다.
    private var mInterface: MyInterface? = null

    fun setInterface(myInterface: MyInterface) {
        this.mInterface = myInterface
    }

    /**
     * 질문 리스트를 생성하는 메소드
     */
    fun createQuestionList() {
        questionList = mInterface?.getQuestionList()!!
    }

    fun updateQuestion(position: Int) {

    }

    fun deleteQuestion(position: Int) {

    }

    fun deleteAllQuestion() {
        questionList.clear()
    }

    fun getQuestionList() = this.questionList
}