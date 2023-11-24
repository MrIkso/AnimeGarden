package com.xiaoyv.anime.garden.ui.anime.overview

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.SpanUtils
import com.chad.library.adapter.base.util.addOnDebouncedChildClick
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.databinding.FragmentAnimeInfoOverviewBinding
import com.xiaoyv.anime.garden.helper.RecyclerItemTouchedListener
import com.xiaoyv.anime.garden.kts.GoogleAttr
import com.xiaoyv.anime.garden.ui.anime.AnimeInfoActivity
import com.xiaoyv.anime.garden.ui.anime.AnimeInfoViewModel
import com.xiaoyv.anime.garden.ui.anime.characters.AnimeCharactersAdapter
import com.xiaoyv.anime.garden.ui.anime.staff.AnimeStaffAdapter
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelFragment
import com.xiaoyv.blueprint.constant.NavKey
import com.xiaoyv.widget.kts.getAttrColor
import com.xiaoyv.widget.kts.getParcelObj
import com.xiaoyv.widget.kts.useNotNull

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

        overviewRelationAdapter.addOnDebouncedChildClick(R.id.item_root) { adapter, _, position ->
            useNotNull(adapter.getItem(position)?.node) {
                AnimeInfoActivity.openSelf(this)
            }
        }

        overviewAdapter.addOnItemChildLongClickListener(R.id.item_root) { adapter, _, position ->
            useNotNull(adapter.getItem(position)) {
                ClipboardUtils.copyText(value)
            }
            true
        }
    }

    override fun LifecycleOwner.initViewObserver() {
        viewModel.onMediaLiveData.observe(this) {
            val mediaEntity = it ?: return@observe
            val relations = mediaEntity.relations?.edges.orEmpty()
            val characters = mediaEntity.characterPreview?.edges.orEmpty()
            val staffs = mediaEntity.staffPreview?.edges.orEmpty()

            activityModel.onMediaLiveData.value = mediaEntity

            overviewAdapter.submitList(viewModel.buildOverviewItem(it))

            overviewRelationAdapter.submitList(relations) {
                showRelations = relations.isNotEmpty()
            }

            charactersAdapter.submitList(characters) {
                showCharacters = characters.isNotEmpty()
            }

            staffAdapter.submitList(staffs) {
                showStaffs = staffs.isNotEmpty()
            }

            showTitleAndDescription(mediaEntity)
        }
    }

    /**
     * 展示标题和描述
     */
    private fun showTitleAndDescription(mediaEntity: AnilistMediaEntity) {
        binding.tvDescriptionValue.text = HtmlCompat.fromHtml(
            mediaEntity.description.orEmpty(),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )

        val names = listOf(
            "Romain" to mediaEntity.title?.romaji,
            "English" to mediaEntity.title?.english,
            "Native" to mediaEntity.title?.native,
            "Synonyms" to mediaEntity.synonyms.orEmpty().joinToString(", ")
        ).filter { pair -> pair.second.orEmpty().isNotBlank() }

        SpanUtils.with(binding.tvNamesValue)
            .also {
                val textColor = requireActivity().getAttrColor(GoogleAttr.colorOnSurfaceVariant)
                names.forEachIndexed { index, pair ->
                    val value = pair.second.orEmpty()

                    it.append(pair.first + ":")
                        .setTypeface(Typeface.DEFAULT_BOLD)
                    it.appendLine()
                    it.append(pair.second.toString())
                        .setTypeface(Typeface.DEFAULT)
                        .setClickSpan(textColor, false) {
                            showCopyDialog(arrayOf(value))
                        }
                    if (index != names.size - 1) {
                        it.appendLine()
                        it.appendLine()
                    }
                }
            }.create()
    }

    private fun showCopyDialog(strings: Array<String>) {
        MaterialAlertDialogBuilder(hostActivity)
            .setTitle("Copy")
            .setItems(strings) { _, position ->
                ClipboardUtils.copyText(strings[position])
            }
            .create()
            .show()
    }

    companion object {
        fun newInstance(mediaEntity: AnilistMediaEntity): AnimeOverviewFragment {
            return AnimeOverviewFragment().apply {
                arguments = bundleOf(NavKey.KEY_PARCELABLE to mediaEntity)
            }
        }
    }
}