package com.nelu.quran_data.data.repo

import android.util.Log
import com.nelu.quran_data.data.model.ModelQuranData
import com.nelu.quran_data.data.model.ModelTranslation
import com.nelu.quran_data.data.repo.base.BaseJuz
import com.nelu.quran_data.data.repo.base.BasePage
import com.nelu.quran_data.data.repo.base.BaseQuran
import com.nelu.quran_data.data.repo.base.BaseSurah
import com.nelu.quran_data.utils.parser.QuranInfoParser.readQuran

class RepositoryQuran(
    private val baseJuz: BaseJuz,
    private val basePage: BasePage,
    private val baseSurah: BaseSurah
) : BaseQuran {

    private val repo = RepositoryIndex()

    override fun getQuranDataAll(): List<ModelQuranData> {
        val quran = readQuran()
        return ArrayList<ModelQuranData>(quran.size).apply {
            repo.getAll().forEach { info->
                add(
                    ModelQuranData(
                        id = info.id,
                        juz = info.juz,
                        page = info.page,
                        ayah = info.ayahInSurah,
                        surah = info.surah,
                        arabic = quran[info.id -1],
                        transliteration = quran[info.id -1],
                        translation = listOf(
                            ModelTranslation(
                                "", "", quran[info.id -1]
                            )
                        )
                    )
                )
            }
        }
    }


    override fun getQuranDataSurah(surah: Int): List<ModelQuranData> {
        val quran = readQuran()
        return ArrayList<ModelQuranData>(quran.size).apply {
            repo.getSurah(surah).forEach { info->
                add(
                    ModelQuranData(
                        id = info.id,
                        juz = info.juz,
                        page = info.page,
                        ayah = info.ayahInSurah,
                        surah = info.surah,
                        arabic = quran[info.id -1],
                        transliteration = quran[info.id -1],
                        translation = listOf(
                            ModelTranslation(
                                "", "", quran[info.id -1]
                            )
                        )
                    )
                )
            }
        }
    }

    override fun getQuranDataJuz(juz: Int): List<ModelQuranData> {
        val quran = readQuran()
        return ArrayList<ModelQuranData>(quran.size).apply {
            repo.getJuz(juz).forEach { info->
                add(
                    ModelQuranData(
                        id = info.id,
                        juz = info.juz,
                        page = info.page,
                        ayah = info.ayahInSurah,
                        surah = info.surah,
                        arabic = quran[info.id -1],
                        transliteration = quran[info.id -1],
                        translation = listOf(
                            ModelTranslation(
                                "", "", quran[info.id -1]
                            )
                        )
                    )
                )
            }
        }
    }

    override fun getQuranDataAyah(
        surah: Int,
        ayah: Int
    ): ModelQuranData? {
        val quran = readQuran()
        baseSurah
        return repo.getById(surah, ayah)?.let { info->
            ModelQuranData(
                id = info.id,
                juz = info.juz,
                page = info.page,
                ayah = info.ayahInSurah,
                surah = info.surah,
                arabic = quran[info.id -1],
                transliteration = quran[info.id -1],
                translation = listOf(
                    ModelTranslation(
                        "", "", quran[info.id -1]
                    )
                )
            )
        }
    }

    override fun searchQuranData(query: String): List<ModelQuranData> {
//        val quran = readQuran().filter { it.contains(query, true) }
//
//
//        return ArrayList<ModelQuranData>(quran.size).apply {
//            repo.getAll().forEach { info->
//                add(
//                    ModelQuranData(
//                        id = info.id,
//                        juz = info.juz,
//                        page = info.page,
//                        ayah = info.ayahInSurah,
//                        surah = info.surah,
//                        arabic = quran[info.id -1],
//                        transliteration = quran[info.id -1],
//                        translation = listOf(
//                            ModelTranslation(
//                                "", "", quran[info.id -1]
//                            )
//                        )
//                    )
//                )
//            }
//        }
        return emptyList()
    }

    override fun searchQuranDataSurah(
        surah: Int,
        query: String
    ): List<ModelQuranData> {
        TODO("Not yet implemented")
    }

    override fun searchQuranDataJuz(
        juz: Int,
        query: String
    ): List<ModelQuranData> {
        TODO("Not yet implemented")
    }
}