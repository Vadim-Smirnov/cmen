package net.ginteam.carmen.kotlin.view.activity.costs

import android.util.Log
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CostDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeAttributeModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.presenter.costs.CostDetailsActivityPresenter
import net.ginteam.carmen.view.custom.FilterEditText
import java.util.*

/**
 * Created by vadimsmirnov on 05.04.17.
 */


class CostDetailsActivity : BaseCostDetailsActivity <CostDetailsActivityContract.View,
        CostDetailsActivityContract.Presenter>(), CostDetailsActivityContract.View {

    override var mPresenter: CostDetailsActivityContract.Presenter = CostDetailsActivityPresenter()

    private lateinit var mEditTextCostName: FilterEditText

    override fun setCostInformation(cost: CostTypeModel) {
        super.setCostInformation(cost)

        Log.d("FuelDetailsActivity", "Cost: ${cost.attributes?.size}")
        cost.attributes?.let {
            for (i in 0 until it.size) {
                val currentAttribute: CostTypeAttributeModel = it[i]
                val currentAttributeView = mAttributesViews[i]

                currentAttributeView.hint = currentAttribute.title
                currentAttributeView.tag = currentAttribute
            }
        }
    }

    override fun setHistoryInformation(history: HistoryModel, attributesHistory: MutableList<AttributesHistoryModel>) {
        super.setHistoryInformation(history, attributesHistory)

        for (i in 0 until mAttributesViews.size) {
            val currentAttributeHistory: AttributesHistoryModel = attributesHistory[i]
            val currentAttributeView = mAttributesViews[i]
            currentAttributeView.text = currentAttributeHistory.value
        }
    }

    override fun saveHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double) {
        mPresenter.saveFuelHistory(
                cost, date, odometer, comment, price,
                mAttributesViews)
    }

    override fun updateHistory(date: Date, odometer: Int, comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>) {
        mPresenter.updateFuelHistory(
                date, odometer, comment, price,
                mAttributesViews, attributesHistory)
    }

    override fun getLayoutResId(): Int = R.layout.activity_cost_details

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mEditTextCostName = findViewById(R.id.filter_edit_text_cost_name) as FilterEditText

        mAttributesViews.add(mEditTextCostName)
    }

}