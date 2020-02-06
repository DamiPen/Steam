package com.github.damipen.steamapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.damipen.steam.Steam
import com.github.damipen.steam.user.PlayerInfo
import com.github.damipen.steamapi.databinding.ItemAppListBinding
import kotlinx.android.synthetic.main.activity_main.recycler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val key = BuildConfig.STEAM_KEY

    private lateinit var viewModel: ActivityViewModel

//    private val itemDeoc: RecyclerView.ItemDecoration = object : RecyclerView.ItemDecoration(){
//        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//            super.onDraw(c, parent, state)
//            parent.layoutManager.
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        itemDeoc = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        recycler.addItemDecoration(itemDeoc)
        recycler.adapter = AppAdapter()
        recycler.setHasFixedSize(true)

        viewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)

//        viewModel.list.observe(this) {
//            (recycler.adapter as AppAdapter).setList(it)
//        }

        viewModel.friendList.observe(this) {
            (recycler.adapter as AppAdapter).run {
                setList(it)
                notifyDataSetChanged()
            }

        }

        viewModel.getFriends(key)
    }

    override fun onDestroy() {
        super.onDestroy()
//        recycler.removeItemDecoration(itemDeoc)
        recycler.adapter = null
    }

}

class AppAdapter : RecyclerView.Adapter<AppVH>() {

    private val list: MutableList<PlayerInfo> = mutableListOf()

    fun setList(list: List<PlayerInfo>) {
        this.list.clear()
        this.list.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppVH {
        return AppVH(LayoutInflater.from(parent.context).inflate(R.layout.item_app_list, parent, false))
    }

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: AppVH, position: Int) {
        holder.bind.apply {
            val p = list[position]
            Glide.with(picture).load(p.avatarFull).into(picture)
            player = p
            executePendingBindings()
        }
    }

}

class AppVH(item: View) : RecyclerView.ViewHolder(item) {
    val bind = ItemAppListBinding.bind(item)
}

class ActivityViewModel : ViewModel() {

    private val _friendList = MutableLiveData<List<PlayerInfo>>()
    val friendList: LiveData<List<PlayerInfo>>
        get() = _friendList


    fun getFriends(key: String) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    with(Steam(key)) {
                        resolveVanityUrl().steamId?.run {
                            Log.d("ActivityViewModel", "My Steam ID [$this]")
                            return@withContext getFriendsInfo(this).sortedBy { it.personaName }
                        }
                    }
                }
            }
                .onSuccess { _friendList.value = it }
                .onFailure { throw it }
        }
    }


}