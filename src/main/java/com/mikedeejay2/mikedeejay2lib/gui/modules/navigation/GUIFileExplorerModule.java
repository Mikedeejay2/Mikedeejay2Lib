package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.navigation.GUIOpenNewEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.GUIListModule;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.ListViewMode;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class GUIFileExplorerModule extends GUIListModule
{
    private final BiFunction<File, GUIContainer, GUIOpenNewEvent> guiFolderConstructor = (file, gui) -> new GUIOpenNewEvent(plugin, () -> {
        GUIContainer folderGUI = new GUIContainer(plugin, "Folder Navigator", gui.getRows());
        GUIFileExplorerModule explorerModule = new GUIFileExplorerModule(
                plugin, file, viewMode,
                topLeft.getKey(), bottomRight.getKey(),
                topLeft.getValue(), bottomRight.getValue(),
                layerName);
        forwards.forEach(entry -> explorerModule.addForward(entry.getKey(), entry.getValue()));
        backs.forEach(entry -> explorerModule.addBack(entry.getKey(), entry.getValue()));
        folderGUI.addModule(explorerModule);
        if(gui.containsModule(GUINavigatorModule.class))
        {
            folderGUI.addModule(gui.getModule(GUINavigatorModule.class));
        }
        return folderGUI;
    });

    protected File folder;

    public GUIFileExplorerModule(BukkitPlugin plugin, File folder, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol, String layerName)
    {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
        Validate.notNull(folder, "Folder is null");
        Validate.isTrue(folder.isDirectory(), "Provided file is not a directory");
        this.folder = folder;
    }

    public GUIFileExplorerModule(BukkitPlugin plugin, File folder, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol);
        Validate.notNull(folder, "Folder is null");
        Validate.isTrue(folder.isDirectory(), "Provided file is not a directory");
        this.folder = folder;
    }

    public GUIFileExplorerModule(BukkitPlugin plugin, File folder, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        super(plugin, topRow, bottomRow, leftCol, rightCol);
        Validate.notNull(folder, "Folder is null");
        Validate.isTrue(folder.isDirectory(), "Provided file is not a directory");
        this.folder = folder;
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        super.onOpenHead(player, gui);
        gui.setInventoryName(folder.getName());
        resetList();
        fillList(player, gui);
    }

    private void fillList(Player player, GUIContainer gui)
    {
        File[] files = folder.listFiles();
        if(files == null)
        {
            plugin.sendMessage(player, "&c" + plugin.getLibLangManager().getText(player, "command.errors.general"));
            return;
        }
        Queue<GUIItem> folderQueue = new LinkedList<>();
        Queue<GUIItem> fileQueue = new LinkedList<>();
        for(File file : files)
        {
            ItemBuilder fileBuilder = ItemBuilder.of(Base64Head.STACK_OF_PAPER.get())
                    .setName("&f" + file.getName())
                    .setLore(Arrays.stream(file.getPath().split("\\\\"))
                                     .flatMap(s -> Arrays.stream(s.split("/")))
                                     .map(s -> "&7" + s)
                                     .collect(Collectors.toList()));

            GUIItem fileItem = new GUIItem(null);
            if(file.isDirectory())
            {
                fileBuilder.setHeadBase64(Base64Head.FOLDER.get());
                fileItem.setItem(fileBuilder.get());
                fileItem.addEvent(guiFolderConstructor.apply(file, gui));
                folderQueue.add(fileItem);
            }
            else
            {
                String[] splitFile = file.getName().split("\\.");
                String fileType = splitFile[splitFile.length - 1];
                fileBuilder.setHeadBase64(getHeadFromFileExtension(fileType).get());
                fileItem.setItem(fileBuilder.get());
                fileQueue.add(fileItem);
            }
        }
        folderQueue.forEach(this::addListItem);
        fileQueue.forEach(this::addListItem);
    }

    public File getFolder()
    {
        return folder;
    }

    public void setFolder(File folder)
    {
        this.folder = folder;
    }

    private Base64Head getHeadFromFileExtension(String fileType)
    {
        switch(fileType.toLowerCase())
        {
            case "a":
            case "ar":
            case "cpio":
            case "shar":
            case "lbr":
            case "iso":
            case "mar":
            case "sbx":
            case "tar":
            case "bz2":
            case "f":
            case "gz":
            case "lz":
            case "lz4":
            case "lzma":
            case "lzo":
            case "rz":
            case "sfark":
            case "sz":
            case "xz":
            case "z":
            case "Z":
            case "zst":
            case "7z":
            case "s7z":
            case "ace":
            case "afa":
            case "alz":
            case "arc":
            case "ark":
            case "cdx":
            case "arj":
            case "b1":
            case "b6z":
            case "ba":
            case "bh":
            case "cab":
            case "car":
            case "cfs":
            case "cpt":
            case "dar":
            case "dd":
            case "dgc":
            case "dmg":
            case "ear":
            case "gca":
            case "ha":
            case "hki":
            case "ice":
            case "kgb":
            case "lzh":
            case "lha":
            case "lzx":
            case "pak":
            case "partimg":
            case "paq6":
            case "paq7":
            case "paq8":
            case "pea":
            case "phar":
            case "pim":
            case "pit":
            case "qda":
            case "rk":
            case "sda":
            case "sea":
            case "sen":
            case "sfx":
            case "shk":
            case "sit":
            case "sitx":
            case "sqx":
            case "tgz":
            case "tlz":
            case "txz":
            case "uc":
            case "uc0":
            case "uc2":
            case "ucn":
            case "ur2":
            case "ue2":
            case "uca":
            case "uha":
            case "war":
            case "wim":
            case "xar":
            case "xp3":
            case "yz1":
            case "zip":
            case "zipx":
            case "zoo":
            case "zpaq":
            case "zz":
            case "ecc":
            case "ecsbx":
            case "par":
            case "par2":
            case "rev":
                return Base64Head.FOLDER_BLACK;
            case "jar":
                return Base64Head.COFFEE_CUP;
            case "rar":
                return Base64Head.WINRAR_BOOKS;
            case "0":
            case "600":
            case "602":
            case "abw":
            case "acl":
            case "afp":
            case "ami":
            case "ans":
            case "asc":
            case "aww":
            case "ccf":
            case "csv":
            case "cwl":
            case "dbk":
            case "dita":
            case "doc":
            case "docm":
            case "docx":
            case "dot":
            case "dotx":
            case "dwd":
            case "egt":
            case "epub":
            case "ezw":
            case "fdw":
            case "ftm":
            case "ftx":
            case "gdoc":
            case "html":
            case "hwp":
            case "hwpml":
            case "log":
            case "lwp":
            case "mbp":
            case "md":
            case "me":
            case "mcw":
            case "mobi":
            case "nb":
            case "nbp":
            case "neis":
            case "nt":
            case "nq":
            case "odm":
            case "odoc":
            case "odt":
            case "osheet":
            case "ott":
            case "omm":
            case "pages":
            case "pap":
            case "pdax":
            case "pdf":
            case "quox":
            case "rtf":
            case "sdw":
            case "se":
            case "stw":
            case "sxw":
            case "tex":
            case "info":
            case "txt":
            case "uof":
            case "uoml":
            case "via":
            case "wpd":
            case "wps":
            case "wpt":
            case "wrd":
            case "wrf":
            case "wri":
            case "xhtml":
            case "xml":
            case "xps":
            case "yml":
            case "json":
            case "cnf":
            case "conf":
            case "cfg":
            case "cf":
            case "ini":
            case "properties":
            case "hocon":
            case "tml":
            case "toml":
            case "yaml":
                return Base64Head.PAPER_PENCIL;
            case "3gp":
            case "aa":
            case "aac":
            case "aax":
            case "act":
            case "aiff":
            case "alac":
            case "amr":
            case "ape":
            case "au":
            case "awb":
            case "dss":
            case "dvf":
            case "flac":
            case "gsm":
            case "iklax":
            case "ivs":
            case "m4a":
            case "m4b":
            case "mmf":
            case "mp3":
            case "mpc":
            case "msv":
            case "nmf":
            case "ogg":
            case "oga":
            case "mogg":
            case "opus":
            case "org":
            case "ra":
            case "rm":
            case "raw":
            case "rf64":
            case "slm":
            case "tta":
            case "voc":
            case "vox":
            case "wav":
            case "wma":
            case "wv":
            case "8svx":
            case "cda":
                return Base64Head.NOTE_BLUE;
            case "webm":
            case "mkv":
            case "flv":
            case "vob":
            case "drc":
            case "gif":
            case "gifv":
            case "mng":
            case "avi":
            case "mts":
            case "ts":
            case "m2ts":
            case "mov":
            case "qt":
            case "wmv":
            case "yuv":
            case "rmvb":
            case "viv":
            case "asf":
            case "amv":
            case "mp4":
            case "m4p":
            case "mpg":
            case "mpeg":
            case "m2v":
            case "m4v":
            case "svi":
            case "3gpp":
            case "3gpp2":
            case "mxf":
            case "roq":
            case "nsv":
            case "f4v":
            case "f4p":
            case "f4a":
            case "f4b":
                return Base64Head.CAMERA;
            case "dll":
            case "bat":
            case "8bf":
            case "bpl":
            case "class":
            case "bundle":
            case "coff":
            case "com":
            case "dol":
            case "elf":
            case "jeff":
            case "xpi":
            case "o":
            case "obb":
            case "rll":
            case "so":
            case "vap":
            case "xap":
            case "xcoff":
            case "vbx":
            case "ocx":
            case "tlb":
                return Base64Head.GEAR;
            case "bin":
            case "cmd":
            case "cpl":
            case "exe":
            case "gadget":
            case "inf1":
            case "ins":
            case "inx":
            case "isu":
            case "job":
            case "jse":
            case "link":
            case "msc":
            case "paf":
            case "pif":
            case "ps1":
            case "reg":
            case "rgs":
            case "scr":
            case "sct":
            case "shb":
            case "shs":
            case "u3p":
            case "vb":
            case "vbe":
            case "vbs":
            case "vbscript":
            case "ws":
            case "wsf":
            case "wsh":
            case "apk":
            case "app":
            case "bac":
            case "dos":
            case "s1es":
            case "xbe":
            case "xex":
                return Base64Head.ARROW_FORWARD_WHITE;
            case "jpeg":
            case "jfif":
            case "exif":
            case "tiff":
            case "bmp":
            case "png":
            case "ppm":
            case "pgm":
            case "pbm":
            case "pnm":
            case "webp":
            case "heif":
            case "bpg":
            case "deep":
            case "drw":
            case "ecw":
            case "fits":
            case "flif":
            case "ico":
            case "ilbm":
            case "img":
            case "pam":
            case "pcx":
            case "nrrd":
            case "pgf":
            case "plbm":
            case "sgi":
            case "sid":
            case "tga":
            case "vicar":
            case "xisf":
            case "svg":
            case "cgm":
            case "ai":
            case "hpgl":
            case "hvif":
            case "odg":
            case "qcc":
            case "vml":
            case "blend":
            case "dgn":
            case "dwf":
            case "dxf":
            case "flt":
            case "hsf":
            case "iges":
            case "ipa":
            case "jt":
            case "ma":
            case "mb":
            case "obj":
            case "ply":
            case "prc":
            case "step":
            case "skp":
            case "stl":
            case "u3d":
            case "vrml":
            case "xaml":
            case "xgl":
            case "xvl":
            case "xvrml":
            case "3d":
            case "3df":
            case "3ds":
            case "3dxml":
            case "x3d":
            case "eps":
            case "wmf":
            case "pict":
            case "swf":
            case "pns":
            case "mpo":
            case "jps":
                return Base64Head.PAINTING;
            default:
                return Base64Head.STACK_OF_PAPER;
        }
    }
}
