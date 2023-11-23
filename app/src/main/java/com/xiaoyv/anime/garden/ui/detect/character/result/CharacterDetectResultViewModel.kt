package com.xiaoyv.anime.garden.ui.detect.character.result

import com.xiaoyv.anime.garden.api.response.DetectCharacterEntity
import com.xiaoyv.anime.garden.api.response.ListParcelableWrapEntity
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModel

/**
 * Class: [CharacterDetectResultViewModel]
 *
 * @author why
 * @since 11/21/23
 */
class CharacterDetectResultViewModel : BaseViewModel() {

    internal var detectCharacterResult: ListParcelableWrapEntity<DetectCharacterEntity>? = null

}