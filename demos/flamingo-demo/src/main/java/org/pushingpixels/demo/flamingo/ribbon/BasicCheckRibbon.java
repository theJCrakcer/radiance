/*
 * Copyright (c) 2005-2019 Flamingo Kirill Grouchnikov. All Rights Reserved.
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
 *  o Neither the name of Flamingo Kirill Grouchnikov nor the names of
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
package org.pushingpixels.demo.flamingo.ribbon;

import com.jgoodies.forms.builder.FormBuilder;
import org.pushingpixels.demo.flamingo.*;
import org.pushingpixels.demo.flamingo.common.QuickStylesPanel;
import org.pushingpixels.demo.flamingo.svg.tango.transcoded.*;
import org.pushingpixels.flamingo.api.common.*;
import org.pushingpixels.flamingo.api.common.icon.*;
import org.pushingpixels.flamingo.api.common.model.*;
import org.pushingpixels.flamingo.api.common.popup.*;
import org.pushingpixels.flamingo.api.common.popup.PopupPanelManager.*;
import org.pushingpixels.flamingo.api.common.popup.model.*;
import org.pushingpixels.flamingo.api.common.projection.*;
import org.pushingpixels.flamingo.api.ribbon.*;
import org.pushingpixels.flamingo.api.ribbon.model.*;
import org.pushingpixels.flamingo.api.ribbon.projection.*;
import org.pushingpixels.flamingo.api.ribbon.resize.*;
import org.pushingpixels.flamingo.api.ribbon.synapse.model.*;
import org.pushingpixels.flamingo.api.ribbon.synapse.projection.*;
import org.pushingpixels.neon.NeonCortex;
import org.pushingpixels.neon.icon.*;
import org.pushingpixels.substance.api.*;
import org.pushingpixels.substance.api.skin.GeminiSkin;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.List;
import java.util.*;

public class BasicCheckRibbon extends JRibbonFrame {
    private Locale currLocale;

    private ResourceBundle resourceBundle;

    private RibbonGalleryContentModel styleGalleryContentModel;

    private Command pasteCommand;
    private Command alignLeftCommand;
    private Command alignCenterCommand;
    private Command alignRightCommand;
    private Command alignFillCommand;
    private Command menuSaveSelection;
    private Command menuClearSelection;
    private Command applyStyles;

    private Command popupCommand1;
    private Command popupCommand2;
    private Command popupCommand3;
    private Command popupCommand4;
    private Command popupCommand5;
    private CommandMenuContentModel popupMenuContentModel;

    private Command amEntryPrintMemo;
    private Command amEntryPrintCustom;

    private Command amWirelessBluetooth;

    private Command amEntrySaveAsWord;
    private Command amEntrySaveAsHtml;
    private Command amEntrySaveAsOtherFormats;
    private Command amEntrySaveAs;
    private Command amEntryExit;
    private Command amFooterProps;

    private RibbonComboBoxContentModel<String> fontComboBoxModel;
    private RibbonCheckBoxContentModel rulerCheckBoxModel;
    private RibbonSpinnerNumberContentModel indentLeftSpinnerModel;

    private class ExpandActionListener implements CommandAction {
        @Override
        public void commandActivated(CommandActionEvent e) {
            JOptionPane.showMessageDialog(BasicCheckRibbon.this, "Expand button clicked");
        }
    }

    private JRibbonBand getActionBand() {
        JRibbonBand actionBand = new JRibbonBand(resourceBundle.getString("Action.textBandTitle"),
                Document_new.factory(), new ExpandActionListener());

        actionBand.startGroup();
        actionBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("AddressBook.text"))
                        .setIconFactory(Address_book_new.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Address Book activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("NA")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.TOP);

        actionBand.startGroup();
        actionBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Document.text"))
                        .setIconFactory(Document_new.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Document activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("ND")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.TOP);

        actionBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Appointment.text"))
                        .setIconFactory(Appointment_new.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Appointment activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("NP")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        actionBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Bookmark.text"))
                        .setIconFactory(Bookmark_new.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Bookmark activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("NB")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        actionBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Contact.text"))
                        .setIconFactory(Contact_new.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Contact " + "activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("NC")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        List<RibbonBandResizePolicy> resizePolicies = new ArrayList<>();
        resizePolicies.add(new CoreRibbonResizePolicies.Mirror(actionBand));
        resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(actionBand));
        resizePolicies.add(new CoreRibbonResizePolicies.IconRibbonBandResizePolicy(actionBand));
        actionBand.setResizePolicies(resizePolicies);

        return actionBand;
    }

    private JRibbonBand getPreferencesBand() {
        JRibbonBand preferencesBand = new JRibbonBand(
                resourceBundle.getString("Preferences.textBandTitle"),
                Preferences_desktop_font.factory(), new ExpandActionListener());
        preferencesBand.setResizePolicies(
                CoreRibbonResizePolicies.getCorePoliciesRestrictive(preferencesBand));

        preferencesBand.startGroup();

        preferencesBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Accessibility.text"))
                        .setIconFactory(Preferences_desktop_accessibility.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Accessibility activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("Y")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        preferencesBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Assistive.text"))
                        .setIconFactory(Preferences_desktop_assistive_technology.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Assistive activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("E")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        preferencesBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("KeyboardShortcuts.text"))
                        .setIconFactory(Preferences_desktop_keyboard_shortcuts.factory())
                        .setSecondaryContentModel(this.popupMenuContentModel)
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setPopupKeyTip("H")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        preferencesBand.startGroup();

        preferencesBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Font.text"))
                        .setIconFactory(Preferences_desktop_font.factory())
                        .setAction((CommandActionEvent e) -> System.out.println("Font activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("Z")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.TOP);

        preferencesBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Locale.text"))
                        .setIconFactory(Preferences_desktop_locale.factory())
                        .setAction((CommandActionEvent e) -> System.out.println("Locale activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("L")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.TOP);

        preferencesBand.startGroup();

        preferencesBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Screensaver.text"))
                        .setIconFactory(Preferences_desktop_screensaver.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Screensaver activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("V")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        preferencesBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Themes.text"))
                        .setIconFactory(Preferences_desktop_theme.factory())
                        .setAction((CommandActionEvent e) -> System.out.println("Themes activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setActionKeyTip("T")
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        return preferencesBand;
    }

    private JRibbonBand getParagraphBand() {
        JRibbonBand paragraphBand = new JRibbonBand(
                resourceBundle.getString("Paragraph.textBandTitle"), Format_justify_left.factory(),
                null);

        paragraphBand.startGroup(resourceBundle.getString("Indent.text"));

        paragraphBand.addRibbonComponent(new RibbonSpinnerProjection(
                this.indentLeftSpinnerModel,
                ComponentPresentationModel.builder()
                        .setKeyTip("PL")
                        .build()));

        paragraphBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .setIconFactory(Format_justify_right.factory())
                        .setCaption(resourceBundle.getString("IndentRight.text"))
                        .setRichTooltip(RichTooltip.builder()
                                .setTitle(resourceBundle.getString("IndentRight.tooltip.title"))
                                .addDescriptionSection(resourceBundle.getString(
                                        "IndentRight.tooltip.actionParagraph1"))
                                .addDescriptionSection(resourceBundle.getString(
                                        "IndentRight.tooltip.actionParagraph2"))
                                .build())
                        .build(),
                ComponentPresentationModel.builder()
                        .setKeyTip("PR")
                        .build()));

        paragraphBand.startGroup(resourceBundle.getString("Spacing.text"));

        paragraphBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .build(),
                ComponentPresentationModel.builder()
                        .setKeyTip("PB")
                        .build()));

        paragraphBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .build(),
                ComponentPresentationModel.builder()
                        .setKeyTip("PA")
                        .build()));

        return paragraphBand;
    }

    private JRibbonBand getShowHideBand() {
        JRibbonBand showHideBand = new JRibbonBand(
                resourceBundle.getString("ShowHide.textBandTitle"), Format_justify_left.factory(),
                null);

        showHideBand.addRibbonComponent(new RibbonCheckBoxProjection(
                this.rulerCheckBoxModel,
                ComponentPresentationModel.builder().setKeyTip("SR").build()));

        showHideBand.addRibbonComponent(new RibbonCheckBoxProjection(
                RibbonCheckBoxContentModel.builder()
                        .setText(resourceBundle.getString("Gridlines.text"))
                        .build(),
                ComponentPresentationModel.builder().setKeyTip("SG").build()));

        showHideBand.addRibbonComponent(new RibbonCheckBoxProjection(
                RibbonCheckBoxContentModel.builder()
                        .setText(resourceBundle.getString("MessageBar.text"))
                        .setEnabled(false)
                        .build(),
                ComponentPresentationModel.builder().setKeyTip("SM").build()));

        showHideBand.addRibbonComponent(new RibbonCheckBoxProjection(
                RibbonCheckBoxContentModel.builder()
                        .setText(resourceBundle.getString("DocumentMap.text"))
                        .build(),
                ComponentPresentationModel.builder().setKeyTip("SD").build()));

        showHideBand.addRibbonComponent(new RibbonCheckBoxProjection(
                RibbonCheckBoxContentModel.builder()
                        .setText(resourceBundle.getString("Thumbnails.text"))
                        .build(),
                ComponentPresentationModel.builder().setKeyTip("ST").build()));

        return showHideBand;
    }

    private JRibbonBand getApplicationsBand() {
        JRibbonBand applicationsBand = new JRibbonBand(
                resourceBundle.getString("Applications.textBandTitle"),
                Applications_other.factory(), new ExpandActionListener());

        applicationsBand.addRibbonComponent(new RibbonComboBoxProjection(
                RibbonDefaultComboBoxContentModel.<String>builder()
                        .setItems(new String[] { "Tetris", "Minesweeper", "Doom" })
                        .setIconFactory(Applications_games.factory())
                        .setCaption(resourceBundle.getString("Games.text"))
                        .build(),
                ComponentPresentationModel.builder()
                        .setKeyTip("AG")
                        .setResizingAware(true)
                        .setHorizontalAlignment(HorizontalAlignment.FILL)
                        .build()));

        applicationsBand.addRibbonComponent(new RibbonComboBoxProjection(
                RibbonDefaultComboBoxContentModel.<String>builder()
                        .setItems(new String[] { "Firefox", "Opera", "Konqueror" })
                        .setEnabled(false)
                        .setIconFactory(Applications_internet.factory())
                        .setCaption(resourceBundle.getString("Internet.text"))
                        .build(),
                ComponentPresentationModel.builder()
                        .setKeyTip("AI")
                        .setResizingAware(true)
                        .setHorizontalAlignment(HorizontalAlignment.FILL)
                        .build()));

        applicationsBand.addRibbonComponent(new RibbonComboBoxProjection(
                RibbonDefaultComboBoxContentModel.<String>builder()
                        .setItems(new String[] {
                                resourceBundle.getString("Pictures.text"),
                                resourceBundle.getString("Video.text"),
                                resourceBundle.getString("Audio.text") })
                        .setCaption(resourceBundle.getString("Multimedia.text"))
                        .build(),
                ComponentPresentationModel.builder()
                        .setKeyTip("AM")
                        .setResizingAware(true)
                        .setHorizontalAlignment(HorizontalAlignment.FILL)
                        .build()));

        return applicationsBand;
    }

    private JRibbonBand getDocumentBand() {
        JRibbonBand result = new JRibbonBand(resourceBundle.getString("Document.textBandTitle"),
                Applications_office.factory(), new ExpandActionListener());
        result.setExpandButtonKeyTip("FY");
        result.setCollapsedStateKeyTip("ZD");

        result.startGroup();

        CommandToggleGroupModel locationGroup = new CommandToggleGroupModel();
        result.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("DocumentLocal.text"))
                        .setIconFactory(Folder.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Document Local activated"))
                        .inToggleGroup(locationGroup)
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.TOP);

        result.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("DocumentRemote.text"))
                        .setIconFactory(Folder_remote.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Document Remote activated"))
                        .inToggleGroup(locationGroup)
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.TOP);

        result.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("DocumentSaved.text"))
                        .setIconFactory(Folder_saved_search.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Document Saved activated"))
                        .inToggleGroup(locationGroup)
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.TOP);

        result.startGroup();

        result.addRibbonCommand(Command.builder()
                        .setText(resourceBundle.getString("DocumentNew.text"))
                        .setIconFactory(Document_new.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Document New activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        result.addRibbonCommand(
                Command.builder().setText(resourceBundle.getString("DocumentOpen.text"))
                        .setIconFactory(Document_open.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Document Open activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        result.addRibbonCommand(
                Command.builder().setText(resourceBundle.getString("DocumentSave.text"))
                        .setIconFactory(Document_save.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Document Save activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        result.addRibbonCommand(
                Command.builder().setText(resourceBundle.getString("DocumentPrint.text"))
                        .setIconFactory(Document_print.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Document Print activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        result.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("DocumentPrintPreview.text"))
                        .setIconFactory(Document_print_preview.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Document Print Preview activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        result.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("DocumentProperties.text"))
                        .setIconFactory(Document_properties.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Document Properties activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING).build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        result.setResizePolicies(CoreRibbonResizePolicies.getCorePoliciesRestrictive(result));

        return result;
    }

    private JRibbonBand getClipboardBand() {
        JRibbonBand clipboardBand = new JRibbonBand(
                resourceBundle.getString("Clipboard.textBandTitle"), Edit_paste.factory(),
                new ExpandActionListener());
        clipboardBand.setExpandButtonKeyTip("FO");
        clipboardBand
                .setExpandButtonRichTooltip(RichTooltip.builder()
                        .setTitle(resourceBundle.getString("Clipboard.textBandTitle"))
                        .addDescriptionSection(
                                resourceBundle.getString("Clipboard.textBandTooltipParagraph1"))
                        .build());

        clipboardBand.setCollapsedStateKeyTip("ZC");

        CommandButtonProjection pasteCommandProjection = this.pasteCommand.project(
                CommandButtonPresentationModel.builder()
                        .setHorizontalAlignment(SwingConstants.LEADING)
                        .setPopupKeyTip("V").build());
        Map<Command, CommandButtonPresentationModel.Overlay> pasteOverlays = new HashMap<>();
        pasteOverlays.put(this.popupCommand1,
                CommandButtonPresentationModel.overlay().setActionKeyTip("1"));
        pasteOverlays.put(this.popupCommand2,
                CommandButtonPresentationModel.overlay().setActionKeyTip("2"));
        pasteOverlays.put(this.popupCommand3,
                CommandButtonPresentationModel.overlay().setActionKeyTip("3"));
        pasteOverlays.put(this.popupCommand4,
                CommandButtonPresentationModel.overlay().setActionKeyTip("4"));
        pasteOverlays.put(this.popupCommand5,
                CommandButtonPresentationModel.overlay().setActionKeyTip("5"));
        pasteCommandProjection.setCommandOverlays(pasteOverlays);

        clipboardBand.addRibbonCommand(pasteCommandProjection,
                JRibbonBand.PresentationPriority.TOP);

        clipboardBand
                .addRibbonCommand(
                        Command.builder().setText(resourceBundle.getString("Cut.text"))
                                .setIconFactory(Edit_cut.factory())
                                .setAction((CommandActionEvent e) -> System.out.println("Cut!"))
                                .setActionRichTooltip(RichTooltip.builder()
                                        .setTitle(resourceBundle.getString("Cut.text"))
                                        .addDescriptionSection(resourceBundle
                                                .getString("Cut.tooltip.actionParagraph1"))
                                        .build())
                                .setSecondaryContentModel(this.popupMenuContentModel)
                                .setTitleClickAction().build()
                                .project(CommandButtonPresentationModel.builder()
                                        .setHorizontalAlignment(SwingConstants.LEADING)
                                        .setPopupKeyTip("X").build()),
                        JRibbonBand.PresentationPriority.MEDIUM);

        clipboardBand
                .addRibbonCommand(
                        Command.builder().setText(resourceBundle.getString("Copy.text"))
                                .setIconFactory(Edit_copy.factory())
                                .setAction((CommandActionEvent e) -> System.out.println("Copy!"))
                                .setSecondaryContentModel(this.popupMenuContentModel)
                                .setTitleClickSecondary().build()
                                .project(CommandButtonPresentationModel.builder()
                                        .setHorizontalAlignment(SwingConstants.LEADING)
                                        .setPopupKeyTip("C").build()),
                        JRibbonBand.PresentationPriority.MEDIUM);

        List<CommandGroup> formatMenuEntries = new ArrayList<>();

        formatMenuEntries.add(new CommandGroup(this.menuSaveSelection,
                this.menuClearSelection));
        formatMenuEntries.add(new CommandGroup(this.applyStyles));

        CommandButtonProjection formatCommandProjection = Command.builder()
                .setText(resourceBundle.getString("Format.text")).setIconFactory(
                        Edit_paste.factory())
                .setSecondaryContentModel(new CommandMenuContentModel(
                        QuickStylesPanel.getQuickStylesContentModel(resourceBundle, currLocale),
                        formatMenuEntries))
                .setSecondaryRichTooltip(RichTooltip.builder().setTitle(
                        "Main title that can go over multiple lines of text even exceeding the " +
                                "bigger")
                        .addDescriptionSection(
                                "Simple description that can go over multiple lines of text even " +
                                        "exceeding the bigger")
                        .addDescriptionSection(
                                "Second paragraph that can be multiline as well to test this " +
                                        "feature")
                        .setMainIconFactory(Address_book_new.factory())
                        .setFooterIconFactory(Help_browser.factory())
                        .addFooterSection(
                                "Multiline footer description to provide a little bit more " +
                                        "information on this subject")
                        .build())
                .build()
                .project(CommandButtonPresentationModel.builder()
                        .setHorizontalAlignment(SwingConstants.LEADING)
                        .setPopupKeyTip("FP")
                        .setPopupMenuPresentationModel(CommandPopupMenuPresentationModel.builder()
                                .setPanelPresentationModel(
                                        CommandPanelPresentationModel.builder()
                                                .setToShowGroupLabels(false)
                                                .setCommandPresentationState(
                                                        CommandButtonPresentationState.FIT_TO_ICON)
                                                .setCommandIconDimension(48)
                                                .setMaxColumns(5)
                                                .setMaxRows(3).build())
                                .build())
                        .build());

        Map<Command, CommandButtonPresentationModel.Overlay> formatMenuOverlays = new HashMap<>();
        formatMenuOverlays.put(this.menuSaveSelection,
                CommandButtonPresentationModel.overlay().setActionKeyTip("SS"));
        formatMenuOverlays.put(this.menuClearSelection,
                CommandButtonPresentationModel.overlay().setActionKeyTip("SC"));
        formatMenuOverlays.put(this.applyStyles,
                CommandButtonPresentationModel.overlay().setActionKeyTip("SA"));
        formatCommandProjection.setCommandOverlays(formatMenuOverlays);

        clipboardBand.addRibbonCommand(formatCommandProjection,
                JRibbonBand.PresentationPriority.MEDIUM);

        List<RibbonBandResizePolicy> resizePolicies = new ArrayList<>();
        resizePolicies.add(new CoreRibbonResizePolicies.Mirror(clipboardBand));
        resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(clipboardBand));
        clipboardBand.setResizePolicies(resizePolicies);

        return clipboardBand;
    }

    private JRibbonBand getFindBand() {
        JRibbonBand findBand = new JRibbonBand(resourceBundle.getString("Find.textBandTitle"),
                Edit_find.factory());
        findBand.setCollapsedStateKeyTip("ZY");

        findBand.addRibbonCommand(Command.builder()
                .setText(resourceBundle.getString("Search.text")).setIconFactory(
                        System_search.factory())
                .setAction((CommandActionEvent e) -> System.out.println("Search activated"))
                .build()
                .project(CommandButtonPresentationModel.builder()
                        .setHorizontalAlignment(SwingConstants.LEADING)
                        .setActionKeyTip("FD").build()), JRibbonBand.PresentationPriority.TOP);

        findBand.addRibbonCommand(
                Command.builder().setText(resourceBundle.getString("Find.text"))
                        .setIconFactory(Edit_find.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Find activated")).build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        findBand.addRibbonCommand(
                Command.builder().setText(resourceBundle.getString("FindReplace.text"))
                        .setIconFactory(Edit_find_replace.factory())
                        .setAction((CommandActionEvent e) -> System.out.println(
                                "Find Replace activated"))
                        .setEnabled(false).build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        findBand.addRibbonCommand(Command.builder()
                        .setText(resourceBundle.getString("SelectAll.text"))
                        .setIconFactory(Edit_select_all.factory())
                        .setAction((CommandActionEvent e) -> System.out.println("Select All " +
                                "activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        List<RibbonBandResizePolicy> resizePolicies = new ArrayList<>();
        resizePolicies.add(new CoreRibbonResizePolicies.Mirror(findBand));
        resizePolicies.add(new CoreRibbonResizePolicies.IconRibbonBandResizePolicy(findBand));
        findBand.setResizePolicies(resizePolicies);

        return findBand;
    }

    private JRibbonBand getQuickStylesBand() {
        JRibbonBand quickStylesBand = new JRibbonBand(
                resourceBundle.getString("QuickStyles.textBandTitle"),
                Preferences_desktop_theme.factory());
        quickStylesBand.setCollapsedStateKeyTip("ZS");

        quickStylesBand.setResizePolicies(
                CoreRibbonResizePolicies.getCorePoliciesRestrictive(quickStylesBand));

        Map<JRibbonBand.PresentationPriority, Integer> stylesGalleryVisibleCommandCounts =
                new HashMap<>();
        stylesGalleryVisibleCommandCounts.put(JRibbonBand.PresentationPriority.LOW, 1);
        stylesGalleryVisibleCommandCounts.put(JRibbonBand.PresentationPriority.MEDIUM, 2);
        stylesGalleryVisibleCommandCounts.put(JRibbonBand.PresentationPriority.TOP, 2);

        Map<Command, CommandButtonPresentationModel.Overlay> formatMenuOverlays = new HashMap<>();
        formatMenuOverlays.put(this.menuSaveSelection,
                CommandButtonPresentationModel.overlay().setActionKeyTip("SS"));
        formatMenuOverlays.put(this.menuClearSelection,
                CommandButtonPresentationModel.overlay().setActionKeyTip("SC"));
        formatMenuOverlays.put(this.applyStyles,
                CommandButtonPresentationModel.overlay().setActionKeyTip("SA"));

        RibbonGalleryProjection quickStylesGalleryProjection =
                new RibbonGalleryProjection(this.styleGalleryContentModel,
                        RibbonGalleryPresentationModel.builder()
                                .setPreferredVisibleCommandCounts(stylesGalleryVisibleCommandCounts)
                                .setPreferredPopupMaxVisibleCommandRows(3)
                                .setPreferredPopupMaxCommandColumns(3)
                                .setCommandPresentationState(JRibbonBand.BIG_FIXED_LANDSCAPE)
                                .setExpandKeyTip("L")
                                .build());
        quickStylesGalleryProjection.setCommandOverlays(formatMenuOverlays);
        quickStylesBand.addRibbonGallery(quickStylesGalleryProjection,
                JRibbonBand.PresentationPriority.TOP);

        quickStylesBand.addRibbonCommand(
                Command.builder().setText(resourceBundle.getString("Styles1.text"))
                        .setIconFactory(Font_x_generic.factory())
                        .setAction(
                                (CommandActionEvent e) -> System.out.println("Generic activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .setActionKeyTip("SA").build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        quickStylesBand.addRibbonCommand(
                Command.builder().setText(resourceBundle.getString("Styles2.text"))
                        .setIconFactory(Image_x_generic.factory())
                        .setAction((CommandActionEvent e) -> System.out.println("Image activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .setActionKeyTip("SB").build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        final ColorSelectorPopupMenuContentModel.ColorActivationListener colorActivationListener =
                (Color color) -> {
                    System.out.println("Activated color " + color);
                    rulerPanel.selectBackgroundFill(color);
                };
        final ColorSelectorPopupMenuContentModel.ColorPreviewListener colorPreviewListener =
                new ColorSelectorPopupMenuContentModel.ColorPreviewListener() {
                    @Override
                    public void onColorPreviewActivated(Color color) {
                        System.out.println("Preview activated color " + color);
                        rulerPanel.previewBackgroundFill(color);
                    }

                    @Override
                    public void onColorPreviewCanceled() {
                        System.out.println("Preview canceled color");
                        rulerPanel.previewBackgroundFill(null);
                    }
                };

        final Color defaultColor = new Color(0xFFEEEEEE);

        ColorSelectorPopupMenuGroupModel.Builder selectorBuilder =
                ColorSelectorPopupMenuGroupModel.builder();

        selectorBuilder.addCommand(Command.builder()
                .setText(resourceBundle.getString("ColorSelector.textAutomatic"))
                .setIconFactory(ColorResizableIcon.factory(defaultColor))
                .setAction((CommandActionEvent e) -> {
                    colorActivationListener.onColorActivated(defaultColor);
                    JColorSelectorPopupMenu.addColorToRecentlyUsed(defaultColor);
                })
                .setActionPreview(new Command.CommandActionPreview() {
                    @Override
                    public void onCommandPreviewActivated(Command command) {
                        colorPreviewListener.onColorPreviewActivated(Color.black);
                    }

                    @Override
                    public void onCommandPreviewCanceled(Command command) {
                        colorPreviewListener.onColorPreviewCanceled();
                    }
                })
                .build());

        selectorBuilder.addColorSectionWithDerived(
                new ColorSelectorPopupMenuGroupModel.ColorSectionModel(
                        resourceBundle.getString("ColorSelector.textThemeCaption"),
                        new Color[] { new Color(255, 255, 255), new Color(0, 0, 0),
                                new Color(160, 160, 160), new Color(16, 64, 128),
                                new Color(80, 128, 192), new Color(180, 80, 80),
                                new Color(160, 192, 80), new Color(128, 92, 160),
                                new Color(80, 160, 208), new Color(255, 144, 64) }));

        selectorBuilder.addColorSection(
                new ColorSelectorPopupMenuGroupModel.ColorSectionModel(
                        resourceBundle.getString("ColorSelector.textStandardCaption"),
                        new Color[] { new Color(140, 0, 0), new Color(253, 0, 0),
                                new Color(255, 160, 0), new Color(255, 255, 0),
                                new Color(144, 240, 144), new Color(0, 128, 0),
                                new Color(160, 224, 224), new Color(0, 0, 255),
                                new Color(0, 0, 128), new Color(128, 0, 128) }));

        selectorBuilder.addRecentsSection(
                new ColorSelectorPopupMenuGroupModel.ColorSectionModel(
                        resourceBundle.getString("ColorSelector.textRecentCaption")));

        selectorBuilder.addCommand(Command.builder()
                .setText(resourceBundle.getString("ColorSelector.textMoreColor"))
                .setAction((CommandActionEvent e) -> SwingUtilities.invokeLater(() -> {
                    Color color = JColorChooser.showDialog(BasicCheckRibbon.this,
                            "Color chooser", defaultColor);
                    if (color != null) {
                        colorActivationListener.onColorActivated(color);
                        JColorSelectorPopupMenu.addColorToRecentlyUsed(color);
                    }
                }))
                .build());

        ColorSelectorPopupMenuContentModel selectorModel =
                new ColorSelectorPopupMenuContentModel(
                        Collections.singletonList(selectorBuilder.build()));
        selectorModel.setColorActivationListener(colorActivationListener);
        selectorModel.setColorPreviewListener(colorPreviewListener);

        quickStylesBand.addRibbonCommand(new ColorSelectorCommandButtonProjection(
                        ColorSelectorCommand.colorSelectorBuilder()
                                .setText(resourceBundle.getString("Styles3.text"))
                                .setIconFactory(Text_html.factory())
                                .setColorSelectorPopupMenuContentModel(selectorModel)
                                .build(),
                        CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .setPopupKeyTip("SC").build()),
                JRibbonBand.PresentationPriority.MEDIUM);

        return quickStylesBand;
    }

    private JRibbonBand getPreviewBand() {
        JRibbonBand previewBand = new JRibbonBand(resourceBundle.getString("Preview.textBandTitle"),
                new SimpleResizableIcon.FactoryTop());

        previewBand.setResizePolicies(CoreRibbonResizePolicies.getCorePoliciesNone(previewBand));

        previewBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("Preview.text"))
                        .setIconFactory(new SimpleResizableIcon.FactoryTop())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Preview activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .build()),
                JRibbonBand.PresentationPriority.TOP);
        previewBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("SlideShow.text"))
                        .setIconFactory(new SimpleResizableIcon.FactoryTop())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Slide Show activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .build()),
                JRibbonBand.PresentationPriority.TOP);

        return previewBand;
    }

    private JRibbonBand getAlignmentBand() {
        JRibbonBand alignmentBand = new JRibbonBand(
                resourceBundle.getString("Alignment.textTaskTitle"), Format_justify_left.factory(),
                null);

        alignmentBand.startGroup();

        alignmentBand.addRibbonComponent(new RibbonComboBoxProjection(
                RibbonDefaultComboBoxContentModel.<String>builder()
                        .setItems(new String[] { resourceBundle.getString("VeryLong.text") })
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.FILL)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .setCaption(resourceBundle.getString("Leading.text"))
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.LEADING)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .setCaption(resourceBundle.getString("Trailing.text"))
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.TRAILING)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonComboBoxProjection(
                RibbonDefaultComboBoxContentModel.<String>builder()
                        .setItems(new String[] { resourceBundle.getString("VeryLong.text") })
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.FILL)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .setCaption(resourceBundle.getString("Leading.text"))
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .setCaption(resourceBundle.getString("Trailing.text"))
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.FILL)
                        .build()));

        alignmentBand.startGroup();

        alignmentBand.addRibbonComponent(new RibbonComboBoxProjection(
                RibbonDefaultComboBoxContentModel.<String>builder()
                        .setItems(new String[] { resourceBundle.getString("Long.text") })
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.FILL)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.LEADING)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.TRAILING)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonComboBoxProjection(
                RibbonDefaultComboBoxContentModel.<String>builder()
                        .setItems(new String[] { resourceBundle.getString("Long.text") })
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.FILL)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .build()));

        alignmentBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerNumberContentModel.builder()
                        .setValues(0, 0, 100, 5)
                        .build(),
                ComponentPresentationModel.builder()
                        .setHorizontalAlignment(HorizontalAlignment.FILL)
                        .build()));

        return alignmentBand;
    }

    private JRibbonBand getAnimationBand() {
        JRibbonBand animationBand = new JRibbonBand(
                resourceBundle.getString("Animation.textBandTitle"),
                new SimpleResizableIcon.FactoryTop());

        animationBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("CustomAnimation.text"))
                        .setIconFactory(new SimpleResizableIcon.FactoryTop())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Animation 1 activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .build()),
                JRibbonBand.PresentationPriority.TOP);
        animationBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("CustomAnimation.text"))
                        .setIconFactory(new SimpleResizableIcon.FactoryTop())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Animation 2 activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .build()),
                JRibbonBand.PresentationPriority.TOP);
        animationBand.addRibbonCommand(
                Command.builder()
                        .setText(resourceBundle.getString("CustomAnimation.text"))
                        .setIconFactory(new SimpleResizableIcon.FactoryTop())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Animation 3 activated"))
                        .build()
                        .project(CommandButtonPresentationModel.builder()
                                .setHorizontalAlignment(SwingConstants.LEADING)
                                .build()),
                JRibbonBand.PresentationPriority.TOP);

        animationBand.setResizePolicies(
                CoreRibbonResizePolicies.getCorePoliciesNone(animationBand));

        return animationBand;
    }

    private JRibbonBand getTransitionBand() {
        JRibbonBand transitionBand = new JRibbonBand(
                resourceBundle.getString("TransitionToThis.textBandTitle"),
                new SimpleResizableIcon.FactoryTop());

        List<CommandGroup> transitionGalleryCommands = new ArrayList<>();

        List<Command> transitionGalleryCommandsList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            final int index = i;
            ResizableIconFactory iconFactory = DecoratedResizableIcon.factory(
                    Appointment_new.factory(),
                    (Component c, Graphics g, int x, int y, int width, int height) -> {
                        Graphics2D g2d = (Graphics2D) g.create();
                        NeonCortex.installDesktopHints(g2d);
                        g2d.setFont(SubstanceCortex.GlobalScope.getFontPolicy().getFontSet(null)
                                .getControlFont().deriveFont(9.0f));
                        g2d.setColor(Color.black);
                        g2d.drawString("" + index, x + 1, y + height - 2);
                        g2d.drawString("" + index, x + 3, y + height - 2);
                        g2d.drawString("" + index, x + 2, y + height - 1);
                        g2d.drawString("" + index, x + 2, y + height - 3);
                        g2d.setColor(Color.white);
                        g2d.drawString("" + index, x + 2, y + height - 2);
                        g2d.dispose();
                    });

            Command ribbonCommand = Command.builder()
                    .setText("Transition " + index)
                    .setIconFactory(iconFactory)
                    .setAction((CommandActionEvent e) ->
                            System.out.println("Activated transition " + index))
                    .setToggle().build();

            transitionGalleryCommandsList.add(ribbonCommand);
        }
        transitionGalleryCommands.add(new CommandGroup(
                resourceBundle.getString("TransitionGallery.textGroupTitle1"),
                transitionGalleryCommandsList));

        List<Command> transitionGalleryButtonsList2 = new ArrayList<>();
        for (int i = 41; i <= 70; i++) {
            final int index = i;
            ResizableIconFactory iconFactory = DecoratedResizableIcon.factory(
                    Appointment_new.factory(),
                    (Component c, Graphics g, int x, int y, int width, int height) -> {
                        Graphics2D g2d = (Graphics2D) g.create();
                        NeonCortex.installDesktopHints(g2d);
                        g2d.setFont(SubstanceCortex.GlobalScope.getFontPolicy().getFontSet(null)
                                .getControlFont().deriveFont(9.0f));
                        g2d.setColor(Color.black);
                        g2d.drawString("" + index, x + 1, y + height - 2);
                        g2d.drawString("" + index, x + 3, y + height - 2);
                        g2d.drawString("" + index, x + 2, y + height - 1);
                        g2d.drawString("" + index, x + 2, y + height - 3);
                        g2d.setColor(Color.white);
                        g2d.drawString("" + index, x + 2, y + height - 2);
                        g2d.dispose();
                    });

            Command ribbonCommand = Command.builder()
                    .setText("Transition " + index)
                    .setIconFactory(iconFactory)
                    .setAction((CommandActionEvent e) ->
                            System.out.println("Activated transition " + index))
                    .setToggle().build();

            transitionGalleryButtonsList2.add(ribbonCommand);
        }
        transitionGalleryCommands.add(new CommandGroup(
                resourceBundle.getString("TransitionGallery.textGroupTitle2"),
                transitionGalleryButtonsList2));

        RibbonGalleryContentModel transitionGalleryContentModel = new RibbonGalleryContentModel(
                null, transitionGalleryCommands);

        Map<JRibbonBand.PresentationPriority, Integer> transitionGalleryVisibleCommandCounts = new
                HashMap<>();
        transitionGalleryVisibleCommandCounts.put(JRibbonBand.PresentationPriority.LOW, 2);
        transitionGalleryVisibleCommandCounts.put(JRibbonBand.PresentationPriority.MEDIUM, 4);
        transitionGalleryVisibleCommandCounts.put(JRibbonBand.PresentationPriority.TOP, 6);

        RibbonGalleryPresentationModel transitionGalleryPresentationModel =
                RibbonGalleryPresentationModel.builder()
                        .setPreferredVisibleCommandCounts(transitionGalleryVisibleCommandCounts)
                        .setPreferredPopupMaxVisibleCommandRows(6)
                        .setPreferredPopupMaxCommandColumns(6)
                        .setCommandPresentationState(CommandButtonPresentationState.SMALL).build();

        transitionBand.addRibbonGallery(
                new RibbonGalleryProjection(
                        transitionGalleryContentModel, transitionGalleryPresentationModel),
                JRibbonBand.PresentationPriority.TOP);

        transitionBand.startGroup();
        transitionBand.addRibbonComponent(
                new RibbonComboBoxProjection(
                        RibbonDefaultComboBoxContentModel.<String>builder()
                                .setItems(new String[] {
                                        "[" + resourceBundle.getString("NoSound.text") + "]     " })
                                .setIconFactory(new SimpleResizableIcon.FactoryTop())
                                .build(),
                        ComponentPresentationModel.withDefaults()));
        transitionBand.addRibbonComponent(
                new RibbonComboBoxProjection(
                        RibbonDefaultComboBoxContentModel.<String>builder()
                                .setItems(new String[] {
                                        resourceBundle.getString("Medium.text") + "           " })
                                .setCaption(resourceBundle.getString("Speed.text"))
                                .build(),
                        ComponentPresentationModel.withDefaults()));

        transitionBand.addRibbonComponent(new RibbonCheckBoxProjection(
                RibbonCheckBoxContentModel.builder()
                        .setText(resourceBundle.getString("ApplyToAll.text"))
                        .build(),
                ComponentPresentationModel.withDefaults()));

        return transitionBand;
    }

    private JRibbonBand getTransitionNextBand() {
        JRibbonBand transitionBand = new JRibbonBand(
                resourceBundle.getString("TransitionToNext.textBandTitle"),
                new SimpleResizableIcon.FactoryTop());

        transitionBand.addRibbonComponent(new RibbonCheckBoxProjection(
                RibbonCheckBoxContentModel.builder()
                        .setText(resourceBundle.getString("OnMouseClick.text"))
                        .setSelected(true)
                        .build(),
                ComponentPresentationModel.withDefaults()));

        transitionBand.addRibbonComponent(new RibbonCheckBoxProjection(
                RibbonCheckBoxContentModel.builder()
                        .setText(resourceBundle.getString("AutoAfter.text"))
                        .build(),
                ComponentPresentationModel.withDefaults()));

        transitionBand.addRibbonComponent(new RibbonSpinnerProjection(
                RibbonSpinnerDateContentModel.builder()
                        .setIconFactory(new SimpleResizableIcon.FactoryTop())
                        .build(),
                ComponentPresentationModel.withDefaults()));

        return transitionBand;
    }

    private void createCommands() {
        MessageFormat mf = new MessageFormat(resourceBundle.getString("TestMenuItem.text"));
        mf.setLocale(currLocale);

        this.popupCommand1 = Command.builder()
                .setText(mf.format(new Object[] { "1" }))
                .setIconFactory(ColorResizableIcon.factory(new Color(0xFF80DEEA)))
                .setAction((CommandActionEvent e) ->
                        System.out.println("Test menu item 1 activated"))
                .build();
        this.popupCommand2 = Command.builder()
                .setText(mf.format(new Object[] { "2" }))
                .setIconFactory(ColorResizableIcon.factory(new Color(0xFF80CBC4)))
                .setAction((CommandActionEvent e) ->
                        System.out.println("Test menu item 2 activated"))
                .build();
        this.popupCommand3 = Command.builder()
                .setText(mf.format(new Object[] { "3" }))
                .setIconFactory(ColorResizableIcon.factory(new Color(0xFFA5D6A7)))
                .setAction((CommandActionEvent e) ->
                        System.out.println("Test menu item 3 activated"))
                .build();
        this.popupCommand4 = Command.builder()
                .setText(mf.format(new Object[] { "4" }))
                .setIconFactory(ColorResizableIcon.factory(new Color(0xFFC5E1A5)))
                .setAction((CommandActionEvent e) ->
                        System.out.println("Test menu item 4 activated"))
                .build();
        this.popupCommand5 = Command.builder()
                .setText(mf.format(new Object[] { "5" }))
                .setIconFactory(ColorResizableIcon.factory(new Color(0xFFE6EE9C)))
                .setAction((CommandActionEvent e) ->
                        System.out.println("Test menu item 5 activated"))
                .build();

        List<Command> menuCommands1 = Arrays.asList(
                this.popupCommand1, this.popupCommand2, this.popupCommand3);
        List<Command> menuCommands2 = Arrays.asList(
                this.popupCommand4, this.popupCommand5);

        this.popupMenuContentModel = new CommandMenuContentModel(
                Arrays.asList(new CommandGroup(menuCommands1),
                        new CommandGroup(menuCommands2)));

        this.pasteCommand = Command.builder()
                .setText(resourceBundle.getString("Paste.text"))
                .setIconFactory(Edit_paste.factory())
                .setAction((CommandActionEvent e) -> System.out.println("Pasted!"))
                .setActionRichTooltip(
                        RichTooltip.builder()
                                .setTitle(resourceBundle.getString("Paste.text"))
                                .addDescriptionSection(resourceBundle.getString(
                                        "Paste.tooltip.actionParagraph1"))
                                .build())
                .setSecondaryContentModel(this.popupMenuContentModel)
                .setSecondaryRichTooltip(RichTooltip.builder()
                        .setTitle(resourceBundle.getString("Paste.text"))
                        .addDescriptionSection(resourceBundle
                                .getString("Paste.tooltip.popupParagraph1"))
                        .build())
                .setTitleClickAction()
                .build();

        // Toggle group for the align left/center/right/fill commands
        CommandToggleGroupModel alignToggleGroup = new CommandToggleGroupModel();

        this.alignLeftCommand = Command.builder()
                .setIconFactory(Format_justify_left.factory())
                .setAction((CommandActionEvent e) -> System.out.println("Align to left"))
                .inToggleGroup(alignToggleGroup)
                .build();

        this.alignCenterCommand = Command.builder()
                .setIconFactory(Format_justify_center.factory())
                .setAction((CommandActionEvent e) -> System.out.println("Align to center"))
                .inToggleGroupAsSelected(alignToggleGroup)
                .build();

        this.alignRightCommand = Command.builder()
                .setIconFactory(Format_justify_right.factory())
                .setAction((CommandActionEvent e) -> System.out.println("Align to right"))
                .inToggleGroup(alignToggleGroup)
                .build();

        this.alignFillCommand = Command.builder()
                .setIconFactory(Format_justify_fill.factory())
                .setAction((CommandActionEvent e) -> System.out.println("Align fill"))
                .inToggleGroup(alignToggleGroup)
                .build();

        this.menuSaveSelection = Command.builder()
                .setText(resourceBundle.getString("Format.menuSaveSelection.text"))
                .setIconFactory(ColorResizableIcon.factory(new Color(0xFFFBC02D)))
                .setAction((CommandActionEvent e) -> System.out.println(
                        "Save Selection activated"))
                .build();
        this.menuClearSelection = Command.builder()
                .setText(resourceBundle.getString("Format.menuClearSelection.text"))
                .setIconFactory(ColorResizableIcon.factory(new Color(0xFFFFA000)))
                .setAction((CommandActionEvent e) -> System.out.println(
                        "Clear Selection activated"))
                .build();
        this.applyStyles = Command.builder()
                .setText(resourceBundle.getString("Format.applyStyles.text"))
                .setIconFactory(ColorResizableIcon.factory(new Color(0xFFF57C00)))
                .setAction((CommandActionEvent e) -> System.out.println(
                        "Apply Styles activated"))
                .build();

        this.amEntryPrintMemo = Command.builder()
                .setText(resourceBundle.getString("AppMenuPrint.memo.text"))
                .setIconFactory(Text_x_generic.factory())
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked memo"))
                .build();

        this.amEntryPrintCustom = Command.builder()
                .setText(resourceBundle.getString("AppMenuPrint.custom.text"))
                .setIconFactory(Text_x_generic.factory())
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked custom"))
                .build();

        this.amWirelessBluetooth = Command.builder()
                .setText(resourceBundle.getString("AppMenuSend.wireless.bluetooth.text"))
                .setIconFactory(Network_wireless.factory())
                .setAction((CommandActionEvent e) -> System.out.println("Bluetooth activated"))
                .build();

        this.amEntrySaveAsWord = Command.builder()
                .setText(resourceBundle.getString("AppMenuSaveAs.word.text"))
                .setIconFactory(X_office_document.factory())
                .setExtraText(resourceBundle.getString("AppMenuSaveAs.word.description"))
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked saved as Word"))
                .build();

        this.amEntrySaveAsHtml = Command.builder()
                .setText(resourceBundle.getString("AppMenuSaveAs.html.text"))
                .setIconFactory(Text_html.factory())
                .setExtraText(resourceBundle.getString("AppMenuSaveAs.html.description"))
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked saved as HTML"))
                .setEnabled(false).build();

        this.amEntrySaveAsOtherFormats = Command.builder()
                .setText(resourceBundle.getString("AppMenuSaveAs.other.text"))
                .setIconFactory(Document_save_as.factory())
                .setExtraText(resourceBundle.getString("AppMenuSaveAs.other.description"))
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked saved as other"))
                .build();

        CommandMenuContentModel saveAsMenu = new CommandMenuContentModel(
                new CommandGroup(
                        resourceBundle.getString("AppMenuSaveAs.secondary.textGroupTitle1"),
                        amEntrySaveAsWord, amEntrySaveAsHtml,
                        amEntrySaveAsOtherFormats));

        this.amEntrySaveAs = Command.builder()
                .setText(resourceBundle.getString("AppMenuSaveAs.text"))
                .setIconFactory(Document_save_as.factory())
                .setAction((CommandActionEvent ae) ->
                        System.out.println("Invoked saving document as"))
                .setTitleClickAction()
                .setSecondaryContentModel(saveAsMenu)
                .build();

        this.amEntryExit = Command.builder()
                .setText(resourceBundle.getString("AppMenuExit.text"))
                .setIconFactory(System_log_out.factory())
                .setAction((CommandActionEvent ae) -> System.exit(0))
                .build();

        this.amFooterProps = Command.builder()
                .setText(resourceBundle.getString("AppMenuOptions.text"))
                .setIconFactory(Document_properties.factory())
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked Options"))
                .build();

        this.fontComboBoxModel = RibbonDefaultComboBoxContentModel.<String>builder()
                .setItems(new String[] {
                        "+ Minor (Calibri)   ", "+ Minor (Columbus)   ",
                        "+ Minor (Consolas)   ", "+ Minor (Cornelius)   ",
                        "+ Minor (Cleopatra)   ", "+ Minor (Cornucopia)   ",
                        "+ Minor (California)   ", "+ Minor (Calendula)   ",
                        "+ Minor (Coriander)   ", "+ Minor (Callisto)   ",
                        "+ Minor (Cajun)   ", "+ Minor (Congola)   ",
                        "+ Minor (Candella)   ", "+ Minor (Cambria)   " })
                .setRichTooltip(RichTooltip.builder()
                        .setTitle(resourceBundle.getString("Seasons.tooltip.title"))
                        .build())
                .build();
        this.fontComboBoxModel.addListDataListener(new ListDataListener() {
            Object selected = fontComboBoxModel.getSelectedItem();

            @Override
            public void intervalAdded(ListDataEvent e) {
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                Object newSelection = fontComboBoxModel.getSelectedItem();
                if (this.selected != newSelection) {
                    System.out.println("New font selection -> " + newSelection);
                    this.selected = newSelection;
                }
            }
        });

        this.rulerCheckBoxModel = RibbonCheckBoxContentModel.builder()
                .setText(resourceBundle.getString("Ruler.text"))
                .setSelected(true)
                .build();

        this.indentLeftSpinnerModel = RibbonSpinnerNumberContentModel.builder()
                .setValues(0, 0, 100, 5)
                .setIconFactory(Format_justify_left.factory())
                .setCaption(resourceBundle.getString("IndentLeft.text"))
                .setRichTooltip(RichTooltip.builder()
                        .setTitle(resourceBundle.getString("IndentLeft.tooltip.title"))
                        .addDescriptionSection(resourceBundle.getString(
                                "IndentLeft.tooltip.actionParagraph1"))
                        .addDescriptionSection(resourceBundle.getString(
                                "IndentLeft.tooltip.actionParagraph2"))
                        .build())
                .build();
    }

    private void createStyleGalleryModel() {
        List<CommandGroup> stylesGalleryCommands = new ArrayList<>();
        List<Command> stylesGalleryCommandsList = new ArrayList<>();
        List<Command> stylesGalleryCommandsList2 = new ArrayList<>();
        MessageFormat mfButtonText = new MessageFormat(
                resourceBundle.getString("StylesGallery.textButton"));
        mfButtonText.setLocale(currLocale);
        for (int i = 0; i < 30; i++) {
            final int index = i;
            ResizableIconFactory iconFactory = DecoratedResizableIcon.factory(
                    Font_x_generic.factory(),
                    (Component c, Graphics g, int x, int y, int width, int height) -> {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setColor(Color.black);
                        NeonCortex.installDesktopHints(g2d);
                        g2d.setFont(SubstanceCortex.GlobalScope.getFontPolicy().getFontSet(null).
                                getControlFont());
                        g2d.drawString("" + index, x + 2, y + height - 2);
                        g2d.dispose();
                    });

            Command ribbonCommand = Command.builder()
                    .setText(mfButtonText.format(new Object[] { i }))
                    .setIconFactory(iconFactory)
                    .setToggle().build();

            if (i < 10) {
                stylesGalleryCommandsList.add(ribbonCommand);
            } else {
                stylesGalleryCommandsList2.add(ribbonCommand);
            }
        }

        stylesGalleryCommands.add(new CommandGroup(
                resourceBundle.getString("StylesGallery.textGroupTitle1"),
                stylesGalleryCommandsList));
        stylesGalleryCommands.add(new CommandGroup(
                resourceBundle.getString("StylesGallery.textGroupTitle2"),
                stylesGalleryCommandsList2));

        this.styleGalleryContentModel = new RibbonGalleryContentModel(Font_x_generic.factory(),
                stylesGalleryCommands);
        this.styleGalleryContentModel.setSelectedCommand(
                stylesGalleryCommandsList.get(1));
        this.styleGalleryContentModel.addCommandActivationListener((Command activated) ->
                System.out.println("*** Command '" + activated.getText() + "' activated! ***"));
        this.styleGalleryContentModel.addCommandPreviewListener(
                new RibbonGalleryContentModel.GalleryCommandActionPreview() {
                    @Override
                    public void onCommandPreviewActivated(Command command) {
                        System.out.println("Preview activated for '" + command.getText() + "'");
                    }

                    @Override
                    public void onCommandPreviewCanceled(Command command) {
                        System.out.println("Preview canceled for '" + command.getText() + "'");
                    }
                });

        this.styleGalleryContentModel.addExtraPopupCommandGroup(
                new CommandGroup(this.menuSaveSelection, this.menuClearSelection));
        this.styleGalleryContentModel.addExtraPopupCommandGroup(
                new CommandGroup(this.applyStyles));
    }

    private RibbonContextualTaskGroup group1;
    private RibbonContextualTaskGroup group2;
    private RulerPanel rulerPanel;

    private JPanel statusBar;

    private JRibbonBand paragraphBand;

    public BasicCheckRibbon() {
        super();
        setApplicationIcon(new Applications_internet());
        currLocale = Locale.getDefault();
        resourceBundle = ResourceBundle
                .getBundle("org.pushingpixels.demo.flamingo.resource.Resources", currLocale);
        setTitle(resourceBundle.getString("Frame.title"));
    }

    public void configureRibbon() {
        this.createCommands();
        this.createStyleGalleryModel();

        JRibbonBand clipboardBand = this.getClipboardBand();
        JRibbonBand quickStylesBand = this.getQuickStylesBand();
        JFlowRibbonBand fontBand = this.getFontBand();
        JRibbonBand documentBand = this.getDocumentBand();
        JRibbonBand findBand = this.getFindBand();
        RibbonTask pageLayoutTask = new RibbonTask(
                resourceBundle.getString("PageLayout.textTaskTitle"), clipboardBand,
                quickStylesBand, fontBand, documentBand, findBand);
        pageLayoutTask.setKeyTip("P");

        JRibbonBand actionBand = this.getActionBand();
        JRibbonBand preferencesBand = this.getPreferencesBand();
        JRibbonBand arrangeBand = this.getApplicationsBand();
        paragraphBand = this.getParagraphBand();
        JRibbonBand showHideBand = this.getShowHideBand();
        RibbonTask writeTask = new RibbonTask(resourceBundle.getString("Write.textTaskTitle"),
                actionBand, preferencesBand, arrangeBand, paragraphBand, showHideBand);
        writeTask.setResizeSequencingPolicy(
                new CoreRibbonResizeSequencingPolicies.CollapseFromLast(writeTask));
        writeTask.setKeyTip("W");

        JRibbonBand previewBand = this.getPreviewBand();
        JRibbonBand animationBand = this.getAnimationBand();
        JRibbonBand transitionBand = this.getTransitionBand();
        JRibbonBand transitionNextBand = this.getTransitionNextBand();
        RibbonTask animationsTask = new RibbonTask(
                resourceBundle.getString("Animations.textTaskTitle"), previewBand, animationBand,
                transitionBand, transitionNextBand);
        animationsTask.setKeyTip("A");

        JRibbonBand alignmentBand = this.getAlignmentBand();
        RibbonTask wrappedTask = new RibbonTask(resourceBundle.getString("Wrapped.textTaskTitle"),
                alignmentBand);
        wrappedTask.setKeyTip("R");

        this.getRibbon().addTask(pageLayoutTask);
        this.getRibbon().addTask(writeTask);
        this.getRibbon().addTask(animationsTask);
        this.getRibbon().addTask(wrappedTask);

        this.getRibbon()
                .addAnchoredCommand(Command.builder()
                        .setText(resourceBundle.getString("Share.title"))
                        .setIconFactory(Internet_mail.factory())
                        .setAction((CommandActionEvent e) -> JOptionPane
                                .showMessageDialog(BasicCheckRibbon.this, "Share button clicked"))
                        .build()
                        .project(CommandButtonPresentationModel.builder().setActionKeyTip(
                                "GS").build()));

        this.getRibbon()
                .addAnchoredCommand(Command.builder()
                        .setIconFactory(Internet_group_chat.factory())
                        .setAction((CommandActionEvent e) -> JOptionPane
                                .showMessageDialog(BasicCheckRibbon.this, "Chat button clicked"))
                        .build()
                        .project(CommandButtonPresentationModel.builder().setActionKeyTip(
                                "GC").build()));

        this.getRibbon().addAnchoredCommand(Command.builder()
                .setIconFactory(Help_browser.factory())
                .setActionRichTooltip(RichTooltip.builder()
                        .setTitle(resourceBundle.getString("Help.tooltip.title"))
                        .addDescriptionSection(
                                resourceBundle.getString("Help.tooltip.actionParagraph"))
                        .build())
                .setAction((CommandActionEvent e) -> JOptionPane
                        .showMessageDialog(BasicCheckRibbon.this, "Help button clicked"))
                .build()
                .project(CommandButtonPresentationModel.builder().setActionKeyTip("GH").build()));

        group1 = new RibbonContextualTaskGroup(
                resourceBundle.getString("Group1.textTaskGroupTitle"), Color.red,
                getContextualRibbonTask(resourceBundle.getString("Task11.textTaskTitle"), "XA"),
                getContextualRibbonTask(resourceBundle.getString("Task12.textTaskTitle"), "XB"));
        group2 = new RibbonContextualTaskGroup(
                resourceBundle.getString("Group2.textTaskGroupTitle"), Color.green,
                getContextualRibbonTask(resourceBundle.getString("Task21.textTaskTitle"), "YA"));
        this.getRibbon().addContextualTaskGroup(group1);
        this.getRibbon().addContextualTaskGroup(group2);

        // application menu
        configureApplicationMenu();

        configureTaskBar();

        this.getRibbon().setOnShowContextualMenuListener(
                new JRibbon.OnShowContextualMenuListener() {
                    private CommandMenuContentModel build(Command... commands) {
                        CommandGroup commandGroup = new CommandGroup(commands);

                        if (getRibbon().isMinimized()) {
                            commandGroup.addCommand(Command.builder()
                                    .setText(resourceBundle.getString("ContextMenu.showRibbon"))
                                    .setAction((CommandActionEvent event) ->
                                            getRibbon().setMinimized(false))
                                    .build());
                        } else {
                            commandGroup.addCommand(Command.builder()
                                    .setText(resourceBundle.getString("ContextMenu.hideRibbon"))
                                    .setAction((CommandActionEvent event) ->
                                            getRibbon().setMinimized(true))
                                    .build());
                        }
                        commandGroup.addCommand(Command.builder()
                                .setText(resourceBundle.getString("ContextMenu.configureRibbon"))
                                .setAction((CommandActionEvent event) ->
                                        JOptionPane.showMessageDialog(BasicCheckRibbon.this,
                                                "Configure ribbon option selected"))
                                .build());
                        return new CommandMenuContentModel(commandGroup);
                    }

                    @Override
                    public CommandMenuContentModel getContextualMenuContentModel(
                            RibbonGalleryProjection galleryProjection) {
                        Command galleryCommand;
                        if (getRibbon().isShowingInTaskbar(galleryProjection.getContentModel())) {
                            galleryCommand = Command.builder()
                                    .setText(resourceBundle.getString(
                                            "ContextMenu.removeFromTaskbar"))
                                    .setAction((CommandActionEvent event) ->
                                            getRibbon().removeTaskbarGallery(
                                                    galleryProjection.getContentModel()))
                                    .build();
                        } else {
                            RibbonGalleryPresentationModel presentationModel =
                                    RibbonGalleryPresentationModel.builder()
                                            .setPreferredPopupMaxCommandColumns(4)
                                            .setPreferredPopupMaxVisibleCommandRows(2)
                                            .setCommandPresentationState(JRibbonBand.BIG_FIXED)
                                            .build();
                            galleryCommand = Command.builder()
                                    .setText(resourceBundle.getString("ContextMenu.addToTaskbar"))
                                    .setAction((CommandActionEvent event) ->
                                            getRibbon().addTaskbarGalleryDropdown(
                                                    new RibbonGalleryProjection(
                                                            galleryProjection.getContentModel(),
                                                            presentationModel)))
                                    .build();
                        }

                        return build(galleryCommand);
                    }

                    @Override
                    public CommandMenuContentModel getContextualMenuContentModel(
                            ComponentProjection<? extends JComponent, ?
                                    extends ComponentContentModel> componentProjection) {
                        Command componentCommand;
                        if (getRibbon().isShowingInTaskbar(componentProjection.getContentModel())) {
                            componentCommand = Command.builder()
                                    .setText(resourceBundle.getString(
                                            "ContextMenu.removeFromTaskbar"))
                                    .setAction((CommandActionEvent event) ->
                                            getRibbon().removeTaskbarComponent(
                                                    componentProjection.getContentModel()))
                                    .build();
                        } else {
                            componentCommand = Command.builder()
                                    .setText(resourceBundle.getString("ContextMenu.addToTaskbar"))
                                    .setAction((CommandActionEvent event) ->
                                            getRibbon().addTaskbarComponent(componentProjection))
                                    .build();
                        }

                        return build(componentCommand);
                    }

                    @Override
                    public CommandMenuContentModel getContextualMenuContentModel(
                            CommandButtonProjection<? extends Command> commandButtonProjection) {
                        Command commandCommand;
                        if (getRibbon().isShowingInTaskbar(
                                commandButtonProjection.getContentModel())) {
                            commandCommand = Command.builder()
                                    .setText(resourceBundle.getString(
                                            "ContextMenu.removeFromTaskbar"))
                                    .setAction((CommandActionEvent event) ->
                                            getRibbon().removeTaskbarCommand(
                                                    commandButtonProjection.getContentModel()))
                                    .build();
                        } else {
                            commandCommand = Command.builder()
                                    .setText(resourceBundle.getString("ContextMenu.addToTaskbar"))
                                    .setAction((CommandActionEvent event) ->
                                            getRibbon().addTaskbarCommand(
                                                    commandButtonProjection.getContentModel().project()))
                                    .build();
                        }

                        return build(commandCommand);
                    }

                    @Override
                    public CommandMenuContentModel getContextualMenuContentModel() {
                        return build();
                    }
                });

        this.add(getControlPanel(), BorderLayout.EAST);
        this.add(rulerPanel = new RulerPanel(), BorderLayout.CENTER);

        this.configureStatusBar();
    }

    private void configureTaskBar() {
        JRibbon ribbon = this.getRibbon();

        // taskbar components
        ribbon.addTaskbarCommand(this.pasteCommand.project(
                CommandButtonPresentationModel.builder().setActionKeyTip("1").build()));

        ribbon.addTaskbarCommand(Command.builder()
                .setIconFactory(Edit_clear.factory())
                .setAction((CommandActionEvent e) -> System.out.println("Taskbar Clear activated"))
                .setEnabled(false)
                .build()
                .project(CommandButtonPresentationModel.builder()
                        .setActionKeyTip("2").build()));

        ribbon.addTaskbarAppMenuLink(this.amFooterProps);

        ribbon.addTaskbarSeparator();

        ribbon.addTaskbarAppMenuLink(this.amEntryPrintCustom);
        ribbon.addTaskbarAppMenuLink(this.amWirelessBluetooth);
        ribbon.addTaskbarAppMenuLink(this.amEntryExit);

        ribbon.addTaskbarComponent(new RibbonComboBoxProjection(this.fontComboBoxModel,
                ComponentPresentationModel.builder().setKeyTip("5").build()));

        ribbon.addTaskbarComponent(new RibbonCheckBoxProjection(this.rulerCheckBoxModel,
                ComponentPresentationModel.builder().setKeyTip("6").build()));

        // Add the same gallery we have in the first ribbon task to the taskbar, configuring
        // its popup presentation with a 4x2 grid of slightly smaller buttons (instead of a 3x3
        // grid of slightly larger ones in the in-task gallery popup).
        // Content preview and selection is controlled by the same model and is kept in sync
        // along all usages of the gallery content model in our ribbon.
        ribbon.addTaskbarGalleryDropdown(new RibbonGalleryProjection(this.styleGalleryContentModel,
                RibbonGalleryPresentationModel.builder()
                        .setPreferredPopupMaxCommandColumns(4)
                        .setPreferredPopupMaxVisibleCommandRows(2)
                        .setCommandPresentationState(JRibbonBand.BIG_FIXED).build()));

        // Add the same "Save as" command that we have in the application menu to the taskbar
        ribbon.addTaskbarCommand(this.amEntrySaveAs.project(
                CommandButtonPresentationModel.builder().setPopupKeyTip("7").build()));
    }

    private void configureApplicationMenu() {
        Map<Command, CommandButtonPresentationModel.Overlay> applicationMenuOverlays =
                new HashMap<>();
        Map<Command, CommandButtonPresentationState> applicationMenuSecondaryStates =
                new HashMap<>();

        // "Create new" primary
        List<Command> defaultCommands = new ArrayList<>();
        MessageFormat mfDefault = new MessageFormat(
                resourceBundle.getString("AppMenu.default.textButton"));
        mfDefault.setLocale(currLocale);
        for (int i = 0; i < 5; i++) {
            Command command = Command.builder()
                    .setText(mfDefault.format(new Object[] { i }))
                    .setIconFactory(Text_html.factory())
                    .setAction((CommandActionEvent e) ->
                            System.out.println("Creating " + e.getCommand().getText()))
                    .build();
            defaultCommands.add(command);
        }

        CommandMenuContentModel newMenu = new CommandMenuContentModel(
                new CommandGroup(resourceBundle.getString("AppMenu.default.textGroupTitle1"),
                        defaultCommands));

        Command amEntryNew = Command.builder()
                .setText(resourceBundle.getString("AppMenuNew.text"))
                .setIconFactory(Document_new.factory())
                .setAction((CommandActionEvent ae) ->
                        System.out.println("Invoked creating new document"))
                .setSecondaryContentModel(newMenu)
                .setTitleClickAction()
                .build();
        applicationMenuSecondaryStates.put(amEntryNew,
                CommandButtonPresentationState.MEDIUM);
        applicationMenuOverlays.put(amEntryNew,
                CommandButtonPresentationModel.overlay().setActionKeyTip("N"));

        // "Open" primary
        List<Command> historyCommands = new ArrayList<>();
        MessageFormat mf = new MessageFormat(
                resourceBundle.getString("AppMenuOpen.secondary.textButton"));
        mf.setLocale(currLocale);
        for (int i = 0; i < 5; i++) {
            Command command = Command.builder()
                    .setText(mf.format(new Object[] { i }))
                    .setIconFactory(Text_html.factory())
                    .setAction((CommandActionEvent e) ->
                            System.out.println("Opening " + e.getCommand().getText()))
                    .build();
            historyCommands.add(command);
        }

        CommandMenuContentModel historyOpenMenu = new CommandMenuContentModel(
                new CommandGroup(resourceBundle.getString("AppMenuOpen.secondary.textGroupTitle1"),
                        historyCommands));

        Command amEntryOpen = Command.builder()
                .setText(resourceBundle.getString("AppMenuOpen.text"))
                .setIconFactory(Document_open.factory())
                .setAction((CommandActionEvent ae) ->
                        System.out.println("Invoked opening document"))
                .setSecondaryContentModel(historyOpenMenu)
                .setTitleClickAction()
                .build();
        applicationMenuSecondaryStates.put(amEntryOpen,
                CommandButtonPresentationState.MEDIUM);
        applicationMenuOverlays.put(amEntryOpen,
                CommandButtonPresentationModel.overlay().setActionKeyTip("O"));

        // "Save" primary
        Command amEntrySave = Command.builder()
                .setText(resourceBundle.getString("AppMenuSave.text"))
                .setIconFactory(Document_save.factory())
                .setAction((CommandActionEvent ae) ->
                        System.out.println("Invoked saving document"))
                .setEnabled(false).build();
        applicationMenuOverlays.put(amEntrySave,
                CommandButtonPresentationModel.overlay().setActionKeyTip("S"));

        // "Save as" primary + secondaries
        applicationMenuOverlays.put(this.amEntrySaveAsWord,
                CommandButtonPresentationModel.overlay().setActionKeyTip("W"));
        applicationMenuOverlays.put(this.amEntrySaveAsHtml,
                CommandButtonPresentationModel.overlay().setActionKeyTip("H"));
        applicationMenuOverlays.put(this.amEntrySaveAsOtherFormats,
                CommandButtonPresentationModel.overlay().setActionKeyTip("O"));
        applicationMenuOverlays.put(this.amEntrySaveAs,
                CommandButtonPresentationModel.overlay().setActionKeyTip("A").setPopupKeyTip("F"));
        applicationMenuSecondaryStates.put(this.amEntrySaveAs,
                RibbonApplicationMenuCommandButtonProjection.RIBBON_APP_MENU_SECONDARY_LEVEL);

        // "Print" primary + secondaries
        Command amEntryPrintSelect = Command.builder()
                .setText(resourceBundle.getString("AppMenuPrint.print.text"))
                .setIconFactory(Printer.factory())
                .setExtraText(resourceBundle.getString("AppMenuPrint.print.description"))
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked print"))
                .build();

        Command amEntryPrintDefault = Command.builder()
                .setText(resourceBundle.getString("AppMenuPrint.quick.text"))
                .setIconFactory(Printer.factory())
                .setExtraText(resourceBundle.getString("AppMenuPrint.quick.description"))
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked quick"))
                .build();

        Command amEntryPrintPreview = Command.builder()
                .setText(resourceBundle.getString("AppMenuPrint.preview.text"))
                .setIconFactory(Document_print_preview.factory())
                .setExtraText(resourceBundle.getString("AppMenuPrint.preview.description"))
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked preview"))
                .build();

        applicationMenuOverlays.put(amEntryPrintSelect,
                CommandButtonPresentationModel.overlay().setActionKeyTip("P"));
        applicationMenuOverlays.put(amEntryPrintDefault,
                CommandButtonPresentationModel.overlay().setActionKeyTip("Q"));
        applicationMenuOverlays.put(amEntryPrintPreview,
                CommandButtonPresentationModel.overlay().setActionKeyTip("V"));
        applicationMenuOverlays.put(this.amEntryPrintMemo,
                CommandButtonPresentationModel.overlay().setActionKeyTip("M"));
        applicationMenuOverlays.put(this.amEntryPrintCustom,
                CommandButtonPresentationModel.overlay().setActionKeyTip("C"));

        CommandMenuContentModel printMenu = new CommandMenuContentModel(
                new CommandGroup(resourceBundle.getString("AppMenuPrint.secondary.textGroupTitle1"),
                        amEntryPrintSelect, amEntryPrintDefault, amEntryPrintPreview),
                new CommandGroup(resourceBundle.getString("AppMenuPrint.secondary.textGroupTitle2"),
                        this.amEntryPrintMemo, this.amEntryPrintCustom));

        Command amEntryPrint = Command.builder()
                .setText(resourceBundle.getString("AppMenuPrint.text"))
                .setIconFactory(Document_print.factory())
                .setAction((CommandActionEvent ae) ->
                        System.out.println("Invoked printing as"))
                .setTitleClickAction()
                .setSecondaryContentModel(printMenu)
                .build();
        applicationMenuSecondaryStates.put(amEntryPrint,
                RibbonApplicationMenuCommandButtonProjection.RIBBON_APP_MENU_SECONDARY_LEVEL);
        applicationMenuOverlays.put(amEntryPrint,
                CommandButtonPresentationModel.overlay().setActionKeyTip("P").setPopupKeyTip("W"));

        // "Send" primary + secondaries
        Command amEntrySendMail = Command.builder()
                .setText(resourceBundle.getString("AppMenuSend.email.text"))
                .setIconFactory(Mail_message_new.factory())
                .setExtraText(resourceBundle.getString("AppMenuSend.email.description"))
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked email"))
                .build();

        Command amEntrySendHtml = Command.builder()
                .setText(resourceBundle.getString("AppMenuSend.html.text"))
                .setIconFactory(Text_html.factory())
                .setExtraText(resourceBundle.getString("AppMenuSend.html.description"))
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked HTML"))
                .build();

        Command amEntrySendDoc = Command.builder()
                .setText(resourceBundle.getString("AppMenuSend.word.text"))
                .setIconFactory(X_office_document.factory())
                .setExtraText(resourceBundle.getString("AppMenuSend.word.description"))
                .setAction((CommandActionEvent ae) -> System.out.println("Invoked Word"))
                .build();

        applicationMenuOverlays.put(amEntrySendMail,
                CommandButtonPresentationModel.overlay().setActionKeyTip("E"));
        applicationMenuOverlays.put(amEntrySendHtml,
                CommandButtonPresentationModel.overlay().setActionKeyTip("H"));
        applicationMenuOverlays.put(amEntrySendDoc,
                CommandButtonPresentationModel.overlay().setActionKeyTip("W"));

        Command wirelessWiFi = Command.builder()
                .setText(resourceBundle.getString("AppMenuSend.wireless.wifi.text"))
                .setIconFactory(EmptyResizableIcon.factory())
                .setAction((CommandActionEvent e) -> System.out.println("WiFi activated"))
                .build();

        applicationMenuOverlays.put(wirelessWiFi,
                CommandButtonPresentationModel.overlay().setActionKeyTip("W"));
        applicationMenuOverlays.put(this.amWirelessBluetooth,
                CommandButtonPresentationModel.overlay().setActionKeyTip("B"));

        CommandMenuContentModel wirelessPopupMenuContentModel =
                new CommandMenuContentModel(Collections.singletonList(
                        new CommandGroup(wirelessWiFi, this.amWirelessBluetooth)));

        Command amEntrySendWireless = Command.builder()
                .setText(resourceBundle.getString("AppMenuSend.wireless.text"))
                .setIconFactory(Network_wireless.factory())
                .setExtraText(resourceBundle.getString("AppMenuSend.wireless.description"))
                .setSecondaryContentModel(wirelessPopupMenuContentModel)
                .build();
        applicationMenuSecondaryStates.put(amEntrySendWireless,
                RibbonApplicationMenuCommandButtonProjection.RIBBON_APP_MENU_SECONDARY_LEVEL);
        applicationMenuOverlays.put(amEntrySendWireless,
                CommandButtonPresentationModel.overlay().setPopupKeyTip("X"));

        CommandMenuContentModel sendMenu = new CommandMenuContentModel(
                new CommandGroup(resourceBundle.getString("AppMenuSend.secondary.textGroupTitle1"),
                        amEntrySendMail, amEntrySendHtml, amEntrySendDoc,
                        amEntrySendWireless));

        Command amEntrySend = Command.builder()
                .setText(resourceBundle.getString("AppMenuSend.text"))
                .setIconFactory(Mail_forward.factory())
                .setSecondaryContentModel(sendMenu)
                .build();
        applicationMenuSecondaryStates.put(amEntrySend,
                RibbonApplicationMenuCommandButtonProjection.RIBBON_APP_MENU_SECONDARY_LEVEL);
        applicationMenuOverlays.put(amEntrySend,
                CommandButtonPresentationModel.overlay().setPopupKeyTip("D"));

        applicationMenuOverlays.put(this.amEntryExit,
                CommandButtonPresentationModel.overlay().setActionKeyTip("X"));

        RibbonApplicationMenu applicationMenu = new RibbonApplicationMenu(
                new CommandGroup(amEntryNew, amEntryOpen, amEntrySave, this.amEntrySaveAs),
                new CommandGroup(amEntryPrint, amEntrySend),
                new CommandGroup(this.amEntryExit));

        applicationMenu.addFooterCommand(this.amFooterProps);

        try {
            final BufferedImage appMenuButtonTooltipImage = ImageIO
                    .read(BasicCheckRibbon.class.getResource(
                            "/org/pushingpixels/demo/flamingo/ribbon/appmenubutton-tooltip-main" +
                                    ".png"));
            final int appMenuButtonTooltipImageWidth = appMenuButtonTooltipImage.getWidth();
            final int appMenuButtonTooltipImageHeight = appMenuButtonTooltipImage.getHeight();
            final float appMenuButtonTooltipImageRatio = (float) appMenuButtonTooltipImageWidth
                    / (float) appMenuButtonTooltipImageHeight;
            final int appMenuButtonTooltipImageInitialWidth = 160;
            final int appMenuButtonTooltipImageInitialHeight = (int)
                    (appMenuButtonTooltipImageInitialWidth
                            / appMenuButtonTooltipImageRatio);
            final ResizableIcon appMenuRichTooltipMainIcon = new ResizableIcon() {
                private int width;
                private int height;

                @Override
                public int getIconWidth() {
                    return this.width;
                }

                @Override
                public int getIconHeight() {
                    return this.height;
                }

                @Override
                public void setDimension(Dimension newDimension) {
                    this.width = newDimension.width;
                    this.height = newDimension.height;
                }

                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    g.drawImage(appMenuButtonTooltipImage, x, y, getIconWidth(), getIconHeight(),
                            null);
                }
            };
            appMenuRichTooltipMainIcon.setDimension(new Dimension(
                    appMenuButtonTooltipImageInitialWidth, appMenuButtonTooltipImageInitialHeight));

            RibbonApplicationMenuCommandButtonProjection ribbonMenuCommandProjection =
                    new RibbonApplicationMenuCommandButtonProjection(
                            Command.builder()
                                    .setText(resourceBundle.getString("AppMenu.title"))
                                    .setSecondaryRichTooltip(RichTooltip.builder()
                                            .setTitle(resourceBundle.getString(
                                                    "AppMenu.tooltip.title"))
                                            .addDescriptionSection(resourceBundle.getString(
                                                    "AppMenu.tooltip.paragraph1"))
                                            .setMainIconFactory(() -> appMenuRichTooltipMainIcon)
                                            .setFooterIconFactory(Help_browser.factory())
                                            .addFooterSection(resourceBundle.getString(
                                                    "AppMenu.tooltip.footer1"))
                                            .build())
                                    .setSecondaryContentModel(applicationMenu)
                                    .build(),
                            CommandButtonPresentationModel.builder().setPopupKeyTip("F").build());

            ribbonMenuCommandProjection.setCommandOverlays(applicationMenuOverlays);
            ribbonMenuCommandProjection.setSecondaryLevelCommandPresentationState(
                    applicationMenuSecondaryStates);

            this.getRibbon().setApplicationMenuCommand(ribbonMenuCommandProjection);
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    private RibbonTask getContextualRibbonTask(String title, String keyTip) {
        JRibbonBand actionBand = this.getActionBand();
        JRibbonBand arrangeBand = this.getApplicationsBand();
        JRibbonBand previewBand = this.getPreviewBand();
        JRibbonBand transitionBand = this.getTransitionBand();
        RibbonTask task = new RibbonTask(title, actionBand, arrangeBand, previewBand,
                transitionBand);
        task.setKeyTip(keyTip);
        return task;
    }

    private JPanel getControlPanel() {
        FormBuilder builder = FormBuilder.create().
                columns("right:pref, 8dlu, fill:pref:grow").
                rows("p, $lg, p, $lg, p, $lg, p, $lg, p, $lg, p, $lg, p").
                padding(new EmptyBorder(20, 4, 0, 4));

        final JCheckBox group1Visible = new JCheckBox("visible");
        final JCheckBox group2Visible = new JCheckBox("visible");
        group1Visible.addActionListener((ActionEvent e) -> SwingUtilities
                .invokeLater(() -> getRibbon().setVisible(group1, group1Visible.isSelected())));
        group2Visible.addActionListener((ActionEvent e) -> SwingUtilities
                .invokeLater(() -> getRibbon().setVisible(group2, group2Visible.isSelected())));
        builder.add("Group 1").xy(1, 1).add(group1Visible).xy(3, 1);
        builder.add("Group 2").xy(1, 3).add(group2Visible).xy(3, 3);

        builder.addLabel("Skin").xy(1, 5).add(SkinSwitcher.getSkinSwitcher(this)).xy(3, 5);

        final JCheckBox appMenuVisible = new JCheckBox("visible");
        appMenuVisible.setSelected(true);
        appMenuVisible.addActionListener((ActionEvent e) -> SwingUtilities.invokeLater(() -> {
            if (!appMenuVisible.isSelected())
                getRibbon().setApplicationMenuCommand(null);
            else
                configureApplicationMenu();
        }));
        builder.add("App menu").xy(1, 7).add(appMenuVisible).xy(3, 7);

        final JCheckBox taskbarFull = new JCheckBox("full");
        taskbarFull.setSelected(true);
        taskbarFull.addActionListener((ActionEvent e) -> SwingUtilities.invokeLater(() -> {
            if (!taskbarFull.isSelected()) {
                getRibbon().clearTaskbar();
            } else {
                configureTaskBar();
            }
        }));
        JPanel taskbarPanel = new JPanel();
        taskbarPanel.setLayout(new BorderLayout());
        taskbarPanel.add(taskbarFull, BorderLayout.LINE_START);
        builder.add("Taskbar").xy(1, 9).add(taskbarPanel).xy(3, 9);

        JButton changeParagraph = new JButton("change");
        changeParagraph
                .addActionListener((ActionEvent e) -> SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        paragraphBand.setGroupTitle(0, getRandomString(5, 15));
                    }

                    private String getRandomString(int minLength, int maxLength) {
                        Random random = new Random();
                        int len = minLength + random.nextInt(maxLength - minLength);
                        String newTitle = new String();
                        String letters = "abcdefghijklmnopqrstubvwxyz       ";
                        for (int i = 0; i < len; i++) {
                            newTitle += letters.charAt(random.nextInt(letters.length()));
                        }
                        return newTitle;
                    }
                }));
        builder.add("Change 'Paragraph'").xy(1, 11).add(changeParagraph).xy(3, 11);

        JComboBox localeSwitcher = LocaleSwitcher.getLocaleSwitcher((Locale selected) -> {
            currLocale = selected;
            resourceBundle = ResourceBundle
                    .getBundle("org.pushingpixels.demo.flamingo.resource.Resources", currLocale);
            for (Window window : Window.getWindows()) {
                window.applyComponentOrientation(ComponentOrientation.getOrientation(currLocale));
                SwingUtilities.updateComponentTreeUI(window);
            }
        });
        builder.add("Locale").xy(1, 13).add(localeSwitcher).xy(3, 13);

        return builder.build();
    }

    private JFlowRibbonBand getFontBand() {
        JFlowRibbonBand fontBand = new JFlowRibbonBand(
                resourceBundle.getString("Font.textBandTitle"), Preferences_desktop_font.factory(),
                new ExpandActionListener());
        fontBand.setExpandButtonKeyTip("FN");
        fontBand.setCollapsedStateKeyTip("ZF");

        fontBand.addFlowComponent(new RibbonComboBoxProjection(this.fontComboBoxModel,
                ComponentPresentationModel.builder().setKeyTip("SF").build()));

        fontBand.addFlowComponent(new RibbonComboBoxProjection(
                RibbonDefaultComboBoxContentModel.<String>builder()
                        .setItems(new String[] { "11  " }).build(),
                ComponentPresentationModel.builder().setKeyTip("SS").build()));

        Command indentLeft = Command.builder()
                .setIconFactory(Format_indent_less.factory())
                .setAction((CommandActionEvent e) -> System.out.println("<- Left"))
                .build();
        Command indentRight = Command.builder()
                .setIconFactory(Format_indent_more.factory())
                .setAction((CommandActionEvent e) -> System.out.println("-> Right"))
                .build();
        Map<Command, CommandButtonPresentationModel.Overlay> indentOverlays = new HashMap<>();
        indentOverlays.put(indentLeft,
                CommandButtonPresentationModel.overlay().setActionKeyTip("AO"));
        indentOverlays.put(indentRight,
                CommandButtonPresentationModel.overlay().setActionKeyTip("AI"));

        CommandStripProjection indentStripProjection = new CommandStripProjection(
                new CommandGroup(indentLeft, indentRight),
                CommandStripPresentationModel.builder().build());
        indentStripProjection.setCommandOverlays(indentOverlays);
        fontBand.addFlowComponent(indentStripProjection);

        Command styleBold =
                Command.builder()
                        .setIconFactory(Format_text_bold.factory())
                        .setAction((CommandActionEvent e) -> System.out.println("Bold toggled"))
                        .setToggleSelected(true)
                        .setActionRichTooltip(RichTooltip.builder()
                                .setTitle(resourceBundle.getString(
                                        "FontBold.tooltip.textActionTitle"))
                                .addDescriptionSection(resourceBundle.getString(
                                        "FontBold.tooltip.textActionParagraph1"))
                                .build())
                        .build();

        Command styleItalic =
                Command.builder()
                        .setIconFactory(Format_text_italic.factory())
                        .setAction((CommandActionEvent e) -> System.out.println("Italic toggled"))
                        .setToggle()
                        .setActionRichTooltip(RichTooltip.builder()
                                .setTitle(resourceBundle.getString(
                                        "FontItalic.tooltip.textActionTitle"))
                                .addDescriptionSection(resourceBundle.getString(
                                        "FontItalic.tooltip.textActionParagraph1"))
                                .build())
                        .build();

        Command styleUnderline =
                Command.builder()
                        .setIconFactory(Format_text_underline.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Underline toggled"))
                        .setToggle()
                        .setActionRichTooltip(RichTooltip.builder()
                                .setTitle(resourceBundle.getString(
                                        "FontUnderline.tooltip.textActionTitle"))
                                .addDescriptionSection(resourceBundle.getString(
                                        "FontUnderline.tooltip.textActionParagraph1"))
                                .build())
                        .build();

        Command styleStrikethrough =
                Command.builder()
                        .setIconFactory(Format_text_strikethrough.factory())
                        .setAction((CommandActionEvent e) ->
                                System.out.println("Strikethrough toggled"))
                        .setToggle()
                        .setActionRichTooltip(RichTooltip.builder()
                                .setTitle(resourceBundle.getString(
                                        "FontStrikethrough.tooltip.textActionTitle"))
                                .addDescriptionSection(resourceBundle.getString(
                                        "FontStrikethrough.tooltip.textActionParagraph1"))
                                .build())
                        .build();

        Map<Command, CommandButtonPresentationModel.Overlay> styleOverlays = new HashMap<>();
        styleOverlays.put(styleBold, CommandButtonPresentationModel.overlay().setActionKeyTip("1"));
        styleOverlays.put(styleItalic,
                CommandButtonPresentationModel.overlay().setActionKeyTip("2"));
        styleOverlays.put(styleUnderline,
                CommandButtonPresentationModel.overlay().setActionKeyTip("3"));
        styleOverlays.put(styleStrikethrough,
                CommandButtonPresentationModel.overlay().setActionKeyTip("4"));

        CommandStripProjection styleStripProjection = new CommandStripProjection(
                new CommandGroup(styleBold, styleItalic, styleUnderline,
                        styleStrikethrough),
                CommandStripPresentationModel.builder().build());
        styleStripProjection.setCommandOverlays(styleOverlays);
        fontBand.addFlowComponent(styleStripProjection);

        Map<Command, CommandButtonPresentationModel.Overlay> alignOverlays = new HashMap<>();
        alignOverlays.put(this.alignLeftCommand,
                CommandButtonPresentationModel.overlay().setActionKeyTip("AL"));
        alignOverlays.put(this.alignCenterCommand,
                CommandButtonPresentationModel.overlay().setActionKeyTip("AC"));
        alignOverlays.put(this.alignRightCommand,
                CommandButtonPresentationModel.overlay().setActionKeyTip("AR"));
        alignOverlays.put(this.alignFillCommand,
                CommandButtonPresentationModel.overlay().setActionKeyTip("AF"));

        CommandStripProjection alignStripProjection = new CommandStripProjection(
                new CommandGroup(this.alignLeftCommand, this.alignCenterCommand,
                        this.alignRightCommand, this.alignFillCommand),
                CommandStripPresentationModel.builder().build());
        alignStripProjection.setCommandOverlays(alignOverlays);
        fontBand.addFlowComponent(alignStripProjection);

        return fontBand;
    }

    /**
     * Main method for testing.
     *
     * @param args Ignored.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            SubstanceCortex.GlobalScope.setSkin(new GeminiSkin());

            final BasicCheckRibbon c = new BasicCheckRibbon();
            c.configureRibbon();
            c.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
            Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getMaximumWindowBounds();
            c.setPreferredSize(new Dimension(r.width, r.height / 2));
            c.setMinimumSize(new Dimension(r.width / 10, r.height / 4));
            c.pack();
            c.setLocation(r.x, r.y);
            c.setVisible(true);
            c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            KeyStroke keyStroke = (NeonCortex.getPlatform() == NeonCortex
                    .Platform.MACOS) ? KeyStroke.getKeyStroke("meta alt E") :
                    KeyStroke.getKeyStroke("alt shift E");
            c.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                    .put(keyStroke, "installTracingRepaintManager");
            c.getRootPane().getActionMap().put("installTracingRepaintManager",
                    new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            RepaintManager.setCurrentManager(new TracingRepaintManager());
                        }
                    });
        });
    }

    private void configureStatusBar() {
        statusBar = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        SubstanceCortex.ComponentOrParentChainScope.setDecorationType(statusBar,
                SubstanceSlices.DecorationAreaType.FOOTER);

        JLabel helper = new JLabel("Right click to show menu");
        statusBar.add(helper);

        JComponent alignStrip = new CommandStripProjection(
                new CommandGroup(this.alignLeftCommand, this.alignCenterCommand,
                        this.alignRightCommand, this.alignFillCommand),
                CommandStripPresentationModel.builder()
                        .setCommandPresentationState(CommandButtonPresentationState.SMALL)
                        .setOrientation(
                                CommandStripPresentationModel.StripOrientation.HORIZONTAL).build())
                .buildComponent();

        statusBar.add(alignStrip);

        // This set tracks the current selection in the popup menu for logging purposes.
        // The source of truth on which commands are selected is each individual command
        // that is used in the popup menu content model.
        final Set<String> selection = new HashSet<>();

        List<Command> commands1 = new ArrayList<>();
        List<Command> commands2 = new ArrayList<>();
        List<Command> commands3 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Command command = Command.builder()
                    .setText("option " + i)
                    .setAction((CommandActionEvent ae) -> {
                        // get the command behind this event and check its selected state
                        Command source = ae.getCommand();
                        boolean isSelectedNow = source.isToggleSelected();
                        if (isSelectedNow) {
                            // add the title of the newly selected command
                            selection.add(source.getText());
                        } else {
                            // remove the title of the newly deselected command
                            selection.remove(source.getText());
                        }
                    })
                    .setToggle().build();

            if (i <= 3) {
                commands1.add(command);
            } else if (i <= 7) {
                commands2.add(command);
            } else {
                commands3.add(command);
            }
        }

        CommandMenuContentModel popupMenuContentModel =
                new CommandMenuContentModel(Arrays.asList(
                        new CommandGroup(commands1),
                        new CommandGroup(commands2),
                        new CommandGroup(commands3)));

        statusBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    processPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    processPopup(e);
                }
            }

            private void processPopup(MouseEvent me) {
                Point pt = new Point(me.getPoint());
                SwingUtilities.convertPointToScreen(pt, statusBar);

                CommandPopupMenuPresentationModel popupMenuPresentationModel =
                        CommandPopupMenuPresentationModel.builder()
                                .setToDismissOnCommandActivation(false).build();

                final JCommandPopupMenu menu = new CommandPopupMenuProjection(popupMenuContentModel,
                        popupMenuPresentationModel).buildComponent();

                Popup popup = PopupFactory.getSharedInstance().getPopup(statusBar, menu, pt.x,
                        pt.y - menu.getPreferredSize().height);
                PopupPanelManager.defaultManager().addPopup(statusBar, popup, menu);

                PopupListener tracker = new PopupListener() {
                    @Override
                    public void popupShown(PopupEvent event) {
                    }

                    @Override
                    public void popupHidden(PopupEvent event) {
                        if (event.getSource() == menu) {
                            System.out.print("Current selection: ");
                            for (String selectedTitle : selection) {
                                System.out.print("'" + selectedTitle + "' ");
                            }
                            System.out.println();
                        }
                        PopupPanelManager.defaultManager().removePopupListener(this);
                    }
                };
                PopupPanelManager.defaultManager().addPopupListener(tracker);
            }
        });

        this.add(statusBar, BorderLayout.SOUTH);
    }

    public static class RulerPanel extends JPanel {
        private Color line = Color.darkGray;
        private Color previewFill = null;
        private Color selectFill = new Color(200, 200, 200);
        private Color backgroundFill = selectFill;

        private void setBackgroundFill(Color backgroundFill) {
            this.backgroundFill = backgroundFill;
            float brightness = Color.RGBtoHSB(this.backgroundFill.getRed(),
                    this.backgroundFill.getGreen(), this.backgroundFill.getBlue(), null)[2];
            this.line = (brightness > 0.7f) ? Color.darkGray : Color.white;
            this.invalidate();
            this.repaint();
        }

        public void previewBackgroundFill(Color previewFill) {
            this.previewFill = previewFill;
            this.setBackgroundFill((this.previewFill != null) ? this.previewFill : this.selectFill);
        }

        public void selectBackgroundFill(Color selectFill) {
            this.previewFill = null;
            this.selectFill = selectFill;
            this.setBackgroundFill(this.selectFill);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            NeonCortex.installDesktopHints(g2d);

            g2d.setColor(backgroundFill);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(line);

            if (getComponentOrientation().isLeftToRight()) {
                // horizontal ruler on top
                int offset = 20;
                for (int i = offset; i < this.getWidth(); i += 10) {
                    if ((i - offset) % 100 == 0)
                        continue;
                    g2d.drawLine(i, 9, i, 11);
                }
                for (int i = offset + 50; i < this.getWidth(); i += 100) {
                    g2d.drawLine(i, 7, i, 13);
                }
                for (int i = offset; i < this.getWidth(); i += 100) {
                    int c = ((i - offset) / 100) % 10;
                    g2d.drawString("" + c, i - 2, 15);
                }

                // vertical ruler on left
                for (int i = offset; i < this.getHeight(); i += 10) {
                    if ((i - offset) % 100 == 0)
                        continue;
                    g2d.drawLine(9, i, 11, i);
                }
                for (int i = offset + 50; i < this.getHeight(); i += 100) {
                    g2d.drawLine(7, i, 13, i);
                }
                for (int i = offset; i < this.getHeight(); i += 100) {
                    int c = ((i - offset) / 100) % 10;
                    g2d.drawString("" + c, 8, i + 4);
                }
            } else {
                // horizontal ruler on top
                int offset = 20;
                for (int i = getWidth() - offset; i > 0; i -= 10) {
                    if ((getWidth() - offset - i) % 100 == 0)
                        continue;
                    g2d.drawLine(i, 9, i, 11);
                }
                for (int i = getWidth() - offset - 50; i > 0; i -= 100) {
                    g2d.drawLine(i, 7, i, 13);
                }
                for (int i = getWidth() - offset; i > 0; i -= 100) {
                    int c = ((getWidth() - offset - i) / 100) % 10;
                    g2d.drawString("" + c, i - 2, 15);
                }

                // vertical ruler on right
                for (int i = offset; i < this.getHeight(); i += 10) {
                    if ((i - offset) % 100 == 0)
                        continue;
                    g2d.drawLine(getWidth() - 9, i, getWidth() - 11, i);
                }
                for (int i = offset + 50; i < this.getHeight(); i += 100) {
                    g2d.drawLine(getWidth() - 7, i, getWidth() - 13, i);
                }
                for (int i = offset; i < this.getHeight(); i += 100) {
                    int c = ((i - offset) / 100) % 10;
                    g2d.drawString("" + c, getWidth() - 14, i + 4);
                }
            }
            g2d.dispose();
        }
    }
}
