/*
 * Copyright (c) 2005-2019 Rainbow Kirill Grouchnikov
 * and Alexander Potochkin. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of Rainbow, Kirill Grouchnikov
 *    and Alexander Potochkin nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.pushingpixels.rainbow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import org.pushingpixels.spoonbill.svn.BreadcrumbMultiSvnSelector
import org.pushingpixels.substance.api.SubstanceCortex
import org.pushingpixels.substance.api.SubstanceSlices.AnimationFacet
import org.pushingpixels.substance.api.skin.BusinessSkin
import javax.swing.JFrame

/**
 * SVG viewer application.
 *
 * @author Kirill Grouchnikov
 * @author Alexander Potochkin
 */
fun main(args: Array<String>) {
    GlobalScope.launch(Dispatchers.Swing) {
        JFrame.setDefaultLookAndFeelDecorated(true)
        SubstanceCortex.GlobalScope.setTimelineDuration(1000)
        SubstanceCortex.GlobalScope.allowAnimations(AnimationFacet.GHOSTING_ICON_ROLLOVER)
        SubstanceCortex.GlobalScope.setSkin(BusinessSkin())

        val selector = BreadcrumbMultiSvnSelector(
                BreadcrumbMultiSvnSelector.SvnRepositoryInfo("Oxygen",
                        "svn://anonsvn.kde.org/home/kde/trunk/KDE/kdeartwork/IconThemes/primary/",
                        "anonymous", "anonymous".toCharArray()),
                BreadcrumbMultiSvnSelector.SvnRepositoryInfo("Kalzium",
                        "svn://anonsvn.kde.org/home/kde/trunk/KDE/kdeedu/kalzium/data/",
                        "anonymous", "anonymous".toCharArray()),
                BreadcrumbMultiSvnSelector.SvnRepositoryInfo("Crystal",
                        "svn://anonsvn.kde.org/home/kde/", "anonymous", "anonymous".toCharArray()))
        selector.setThrowsExceptions(true)
        selector.addExceptionHandler { t: Throwable ->
            MessageListDialog.showMessageDialog(null, "Error", t)
        }

        val frame = RainbowViewer<String>("Remote SVG File Viewer", selector)
        frame.setSize(700, 400)
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isVisible = true
    }
}
