package com.xiaoyv.anime.garden.ui.anime.overview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.databinding.FragmentAnimeInfoOverviewBinding
import com.xiaoyv.anime.garden.helper.RecyclerItemTouchedListener
import com.xiaoyv.anime.garden.ui.anime.AnimeInfoActivity
import com.xiaoyv.anime.garden.ui.anime.AnimeInfoViewModel
import com.xiaoyv.anime.garden.ui.anime.characters.AnimeCharactersAdapter
import com.xiaoyv.anime.garden.ui.anime.staff.AnimeStaffAdapter
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelFragment
import com.xiaoyv.blueprint.constant.NavKey
import com.xiaoyv.widget.kts.getParcelObj

/**
 * Class: [AnimeOverviewFragment]
 *
 * @author why
 * @since 11/22/23
 */
@SuppressLint("ClickableViewAccessibility")
class AnimeOverviewFragment :
    BaseViewModelFragment<FragmentAnimeInfoOverviewBinding, AnimeOverviewViewModel>() {

    private val activityModel by activityViewModels<AnimeInfoViewModel>()

    private val parentActivity get() = requireActivity() as AnimeInfoActivity

    private val overviewAdapter by lazy { AnimeOverviewAdapter() }
    private val overviewRelationAdapter by lazy { AnimeOverviewRelationAdapter() }
    private val staffAdapter by lazy { AnimeStaffAdapter() }
    private val charactersAdapter by lazy { AnimeCharactersAdapter() }

    private var showRelations: Boolean = true
        set(value) {
            field = value
            binding.rvRelations.isVisible = value
            binding.tvRelationsTitle.isVisible = value
        }

    private var showCharacters: Boolean = true
        set(value) {
            field = value
            binding.rvCharacters.isVisible = value
            binding.tvCharactersTitle.isVisible = value
        }

    private var showStaffs: Boolean = true
        set(value) {
            field = value
            binding.rvStaffs.isVisible = value
            binding.tvStaffsTitle.isVisible = value
        }

    override fun initArgumentsData(arguments: Bundle) {
        viewModel.onMediaLiveData.value = arguments.getParcelObj(NavKey.KEY_PARCELABLE)
    }

    override fun initView() {
        binding.rvOverview.adapter = overviewAdapter
        binding.rvRelations.adapter = overviewRelationAdapter
        binding.rvCharacters.adapter = charactersAdapter
        binding.rvStaffs.adapter = staffAdapter

        showRelations = false
        showCharacters = true
        showStaffs = true
    }

    override fun initData() {

    }

    override fun initListener() {
        binding.rvOverview.addOnItemTouchListener(RecyclerItemTouchedListener(parentActivity.vp2))
        binding.rvRelations.addOnItemTouchListener(RecyclerItemTouchedListener(parentActivity.vp2))
    }

    override fun LifecycleOwner.initViewObserver() {
        viewModel.onMediaLiveData.observe(this) {
            val mediaEntity = it ?: return@observe
            val relations = mediaEntity.relations?.edges.orEmpty()
            val characters = mediaEntity.characterPreview?.edges.orEmpty()
            val staffs = mediaEntity.staffPreview?.edges.orEmpty()

            activityModel.onMediaLiveData.value = mediaEntity

            overviewAdapter.submitList(viewModel.buildOverviewItem(it))
            overviewRelationAdapter.submitList(relations)
            charactersAdapter.submitList(characters)
            staffAdapter.submitList(staffs)

            showRelations = relations.isNotEmpty()
            showCharacters = characters.isNotEmpty()
            showStaffs = staffs.isNotEmpty()

            binding.tvDescriptionValue.text = HtmlCompat.fromHtml(
                mediaEntity.description.orEmpty(),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }
    }

    companion object {
        fun newInstance(mediaEntity: AnilistMediaEntity): AnimeOverviewFragment {
            return AnimeOverviewFragment().apply {
                arguments = bundleOf(NavKey.KEY_PARCELABLE to mediaEntity)
            }
        }
    }
}