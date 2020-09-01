package owmii.krate.client.screen;

import net.minecraft.util.ResourceLocation;
import owmii.krate.Krate;
import owmii.krate.block.Tier;
import owmii.lib.client.screen.Texture;

import java.util.HashMap;
import java.util.Map;

public class Textures {
    public static final Texture KRATE_9 = register("container/9", 176, 123, 0, 0);
    public static final Texture KRATE_36 = register("container/36", 176, 177, 0, 0);
    public static final Texture KRATE_72 = register("container/72", 176, 249, 0, 0);
    public static final Texture KRATE_144_0 = register("container/144_0", 256, 249, 0, 0);
    public static final Texture KRATE_144_1 = register("container/144_1", 82, 249, 0, 0);
    public static final Texture UP_SLOTS = register("container/widgets", 26, 110, 0, 0);
    public static final Map<Tier, Texture> BG_MAP = new HashMap<>();
    public static final Texture FILTER = register("container/filter", 176, 151, 0, 0);
    public static final Texture WHITE_LIST = register("container/filter", 10, 10, 176, 0);
    public static final Texture BLACK_LIST = register("container/filter", 10, 10, 186, 0);
    public static final Map<Boolean, Texture> MODE_MAP = new HashMap<>();
    public static final Texture CLEAR = register("container/filter", 10, 10, 196, 0);
    public static final Texture TAG_OFF = register("container/filter", 10, 10, 176, 10);
    public static final Texture TAG_ON = register("container/filter", 10, 10, 186, 10);
    public static final Map<Boolean, Texture> TAG_MAP = new HashMap<>();
    public static final Texture NBT_OFF = register("container/filter", 10, 10, 176, 20);
    public static final Texture NBT_ON = register("container/filter", 10, 10, 186, 20);
    public static final Map<Boolean, Texture> NBT_MAP = new HashMap<>();
    public static final Texture COLLECT_BTN = register("container/widgets", 15, 16, 26, 0);
    public static final Texture COLLECT_SET = register("container/collect_set", 176, 144, 0, 0);
    public static final Texture UP_RANGE = register("container/collect_set", 13, 9, 176, 0);
    public static final Texture DOWN_RANGE = register("container/collect_set", 13, 9, 176, 9);
    public static final Texture UP_RANGE_ALL = register("container/collect_set", 23, 9, 189, 0);
    public static final Texture DOWN_RANGE_ALL = register("container/collect_set", 23, 9, 189, 9);
    public static final Texture RESET_RANGE = register("container/collect_set", 23, 11, 176, 18);
    public static final Texture VIS_OFF = register("container/collect_set", 9, 9, 212, 0);
    public static final Texture VIS_ON = register("container/collect_set", 9, 9, 212, 9);
    public static final Map<Boolean, Texture> VIS_MAP = new HashMap<>();
    public static final Texture HOPPER_BTN = register("container/widgets", 15, 16, 26, 16);
    public static final Texture HOPPER_SET = register("container/hopper_set", 176, 179, 0, 0);
    public static final Texture TAB_ON_L = register("container/hopper_set", 26, 26, 176, 0);
    public static final Texture TAB_ON_R = register("container/hopper_set", 26, 26, 202, 0);
    public static final Texture TAB_OFF_L = register("container/hopper_set", 22, 26, 177, 26);
    public static final Texture TAB_OFF_R = register("container/hopper_set", 22, 26, 199, 26);
    public static final Texture HOPPER_SW_BTN = register("container/hopper_set", 21, 13, 177, 52);
    public static final Map<Boolean, Texture> LEFT_TAB_MAP = new HashMap<>();
    public static final Map<Boolean, Texture> RIGHT_TAB_MAP = new HashMap<>();
    public static final Map<Boolean, Map<Boolean, Texture>> TAB_MAP = new HashMap<>();
    public static final Texture COMPACT_BTN = register("container/widgets", 15, 16, 26, 32);
    public static final Texture COMPACT_BTN_2X2 = register("container/widgets", 15, 16, 41, 32);
    public static final Map<Boolean, Texture> COMPACT_TAB = new HashMap<>();

    static Texture register(String path, int width, int height, int u, int v) {
        return new Texture(new ResourceLocation(Krate.MOD_ID, "textures/gui/" + path + ".png"), width, height, u, v);
    }

    static {
        BG_MAP.put(Tier.SMALL, KRATE_9);
        BG_MAP.put(Tier.BASIC, KRATE_36);
        BG_MAP.put(Tier.BIG, KRATE_72);
        BG_MAP.put(Tier.LARGE, Texture.EMPTY.scale(KRATE_144_0.getWidth() + KRATE_144_1.getWidth(), KRATE_144_0.getHeight()));
        MODE_MAP.put(false, WHITE_LIST);
        MODE_MAP.put(true, BLACK_LIST);
        TAG_MAP.put(false, TAG_OFF);
        TAG_MAP.put(true, TAG_ON);
        NBT_MAP.put(false, NBT_OFF);
        NBT_MAP.put(true, NBT_ON);
        VIS_MAP.put(true, VIS_ON);
        VIS_MAP.put(false, VIS_OFF);
        LEFT_TAB_MAP.put(true, TAB_ON_L);
        LEFT_TAB_MAP.put(false, TAB_OFF_L);
        RIGHT_TAB_MAP.put(true, TAB_ON_R);
        RIGHT_TAB_MAP.put(false, TAB_OFF_R);
        TAB_MAP.put(true, LEFT_TAB_MAP);
        TAB_MAP.put(false, RIGHT_TAB_MAP);
        COMPACT_TAB.put(true, COMPACT_BTN_2X2);
        COMPACT_TAB.put(false, COMPACT_BTN);
    }
}
