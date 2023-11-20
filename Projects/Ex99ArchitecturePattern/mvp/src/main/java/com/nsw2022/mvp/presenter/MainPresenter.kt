package com.nsw2022.mvp.presenter

import com.nsw2022.mvp.model.Item
import com.nsw2022.mvp.model.ItemModel

// presenter 라면 가져야 할 기능을 기술한 인터페이스를 구현하여 실제 기능을 작성하는 클래스
class MainPresenter :MainContract.Presenter{

    // presenter는 view와 model을 연결해야 하기에 각각의 참조변수를 멤버로 보유
    var view: MainContract.View? = null
    var model: ItemModel?= null

    // presenter 객체 초기화 작업
    fun initial(view:MainContract.View){
        this.view=view
        model=ItemModel(view.getContext())
    }

    // view의 save 버튼 클릭 이벤트를 대신 처리해주는 기능 메소드 구현
    override fun clickedSave(name: String, email: String) {
        //데이터를 제어하는 model 역할의 객체에게 저장을 요청
        model?.saveData(name,email)
    }

    // view의 load 버튼 클릭 이벤트를 대신 처리해주는 기능메소드 구현
    override fun clickedLoad() {
        // model 에게 data를 요청
        var item:Item? = model?.loadData()

        // view역학을 하는 객체에게 알아서 화면에 보여주도록..
        item?.let { view?.showData(item) }
    }
}