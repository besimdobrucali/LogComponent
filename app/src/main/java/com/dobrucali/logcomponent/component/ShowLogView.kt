package com.dobrucali.logcomponent.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.dobrucali.logcomponent.R
import com.dobrucali.logcomponent.adapter.LogAdapter
import com.dobrucali.logcomponent.getAttributes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.concurrent.atomic.AtomicInteger

private const val DEFAULT_PEEK_HEIGHT = 150

class ShowLogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var logRecyclerView: RecyclerView
    private var logLayout: LinearLayout
    private var logBottomSheetBehavior: BottomSheetBehavior<View>
    private val mAdapter: LogAdapter by lazy {
        LogAdapter()
    }
    private val logId = AtomicInteger(0)

    private val _logList = MutableLiveData<MutableList<LogAdapter.CustomLog>>()
    val logList: LiveData<MutableList<LogAdapter.CustomLog>>
        get() = _logList

    init {
        _logList.value = mutableListOf()

        init(context)

        logLayout = this.findViewById(R.id.log_linear_layout)
        logRecyclerView = this.findViewById(R.id.log_recycler_view)
        logBottomSheetBehavior = BottomSheetBehavior.from(logLayout)

        logRecyclerView.adapter = mAdapter

        getAttributes(context, attrs, R.styleable.ShowLogView) {
            logBottomSheetBehavior.peekHeight =
                elements.getInteger(R.styleable.ShowLogView_peekHeight, DEFAULT_PEEK_HEIGHT)
            logBottomSheetBehavior.isHideable =
                elements.getBoolean(R.styleable.ShowLogView_isHideable, true)
        }
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.show_log_view, this)
    }

    fun addLog(log: String, type: Int) {
        showLogBottomSheetLayout()
        _logList.value?.add(LogAdapter.CustomLog(logId.incrementAndGet(), log, type))
        logList.value?.let {
            mAdapter.submitList(it.reversed().toList())
        }
    }

    private fun showLogBottomSheetLayout() {
        if (logBottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
            logBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

}