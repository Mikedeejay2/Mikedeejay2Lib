package com.mikedeejay2.mikedeejay2lib.util.head;

/**
 * Utility class relating to heads and head Strings
 *
 * @author Mikedeejay2
 */
public final class HeadUtil {
    /**
     * Get a {@link Base64Head} from a file extension. This is based off of
     * many types of file types in a large switch statement.
     *
     * @param fileType The file type to search for
     * @return The <code>Base64Head</code> that was found
     */
    public static Base64Head getHeadFromFileExtension(String fileType) {
        switch(fileType.toLowerCase()) {
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
