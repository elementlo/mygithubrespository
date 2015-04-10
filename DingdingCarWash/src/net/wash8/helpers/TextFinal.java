package net.wash8.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ncb-user on 2014/12/30.
 */
public class TextFinal {
    public static final String LASHUIROUXI_TITLE="车外蜡水快洗";
    public static final String LASHUIROUXI="移动洗车特制水枪，多功能枪头：5Mpa的出水压力，行程为2CM的出水调节圈，水柱类型随意调节；蜂窝孔造型海绵，将赃物控制在蜂窝孔内，洗车不伤车漆。专业调配Meguiar`s(美光)蜡水，100:1的配比产生细腻的泡沫，独特的生物降解配方具备安全有效的去污能力，在全方位清洁您的爱车的同时也令表面保持高亮润泽。整个过程耗时15分钟。注：为了保证洗车质量，建议停车时左右间隔至少40CM，前后间隔1M。";
    public static final String ZHENGCHEJINGXI_TITLE="整车精洗";
    public static final String ZHENGCHEJINGXI="专业玻璃毛巾：超强的细纤维，不易断裂，采用精编细织，不抽丝。专业内饰毛巾：质地柔软不掉棉絮利用毛细现象将物体表面的水迅速吸干，吸水力强大。专业车漆毛巾：优质梭织材料和专利纤维结构避免螺旋纹产生。一台汽车，5条毛巾，不同部位毛巾不混擦。Meguiar`s(美光)内饰清洗剂，完好保持内部塑料、人造革、真皮、橡胶、金属以及音频/视频器材的清新外观和干爽触感。360度无死角关爱您的爱车。整个过程耗时40分钟。";
    public static final String SONAX_TITLE="SONAX（索纳克斯）特级水晶打蜡";
    public static final String SONAX="1、精洗车辆并迅速擦干，不留一丝水渍；2、将SONAX特级水晶蜡倒在海绵上，稍用力在车漆表面画螺纹状，均匀地将产品涂在车漆表面。3、等3到5分钟后（具体时间根据车况定），等产品呈现凝固状态时，用SONAX（索纳克斯）专用打蜡毛巾擦拭。整个过程耗时60—70分钟 用品：纯进口德国SONAX特级水晶蜡，含天然巴西棕榈蜡，采用最新纳米科技，显微镜下呈超细颗粒状，打蜡后纳米颗粒深入车漆并开始形成纳米结构保护膜";
    public static final String MONIDALA_TITLE="全车磨泥打蜡";
    public static final String MONIDALA="步骤：1、整车精洗2、用沥青清除剂清除车身较大，较顽固的颗粒，如柏油、沥青、工业尘土、树胶等3、配合洗车液用去污泥来回擦拭全车车身，待漆面光滑后冲洗全车。4、擦干全车，不留一丝水渍5、全车打上SONAX特级水晶蜡。整个过程耗时90—120分钟。去污泥是由火山灰提炼出，它能快速祛除飞漆、气化树脂和其它混合污染物。";
    public static final String SHENDUHULI_TITLE="内饰深度护理";
    public static final String SHENDUHULI="1、取出脚垫，拆下座椅套。2、全车吸尘，处理地板，座椅上的赃物。3、按照从前到后，从上到下，将车里的内饰用Meguiar`s（美光）内饰清洁剂清洗。4、用Meguiar`s(美光)表板蜡，Meguiar`s(美光)皮革护理等，对车内饰进行护理。5、等候10分钟后将脚垫，座椅套等归位。整个过程耗时90—120分钟。";
    public static final String CHOUYANGXIAODU_TITLE="车内臭氧消毒";
    public static final String CHOUYANGXIAODU="产生的臭氧O3气体能破坏分解细菌的细胞壁，很快地扩散透进细胞将其氧化分解，也可以直接与细菌、病毒发生作用，使细菌代谢繁殖过程遭到破坏。其杀菌能力比氯大600—3000倍，灭菌、消毒作用几乎是瞬时发生。它能净化车内空气、消除烟臭、霉味等，分解苯及二氧化碳等有毒气体，可消除您长时间开车疲劳，令您精神愉快。整个过程耗时10分钟。";
    public static final String LIQINGQUCHU_TITLE="虫胶沥青去除";
    public static final String LIQINGQUCHU="Meguiar`s（美光）特有的设计配方，能渗透分解难以祛除的车身粘着物，具有强力分解作用而不是通过化学反应，在祛除污染物时不伤漆面，安全高效地让您的爱车焕然一新。 整个过程耗时40—60分钟。";
    public static final String JINGXI_NEISHI="全车精细洗车+内饰深度护理";
    public static final String MONIDALA_NEISHI="全车磨泥打蜡+内饰深度护理";
    public static final String SHENDUHULITAOCAN="精细洗车+虫胶沥青去除+四轮毂清洁+皮革上光打蜡+真皮护理+外玻璃深度清洁";

    public static Map<String,String> getSeviceInfomationByIndex(int position){
        Map<String,String> info = new HashMap<String, String>();
        if (position == 0) {
            info.put("TITLE", TextFinal.LASHUIROUXI_TITLE);
            info.put("CONTENT", TextFinal.LASHUIROUXI);
        } else if (position == 1) {
            info.put("TITLE", TextFinal.ZHENGCHEJINGXI_TITLE);
            info.put("CONTENT", TextFinal.ZHENGCHEJINGXI);
        } else if (position == 2) {
            info.put("TITLE", TextFinal.SONAX_TITLE);
            info.put("CONTENT", TextFinal.SONAX);
        } else if (position == 3) {
            info.put("TITLE", TextFinal.MONIDALA_TITLE);
            info.put("CONTENT", TextFinal.MONIDALA);
        } else if (position == 4) {
            info.put("TITLE", TextFinal.SHENDUHULI_TITLE);
            info.put("CONTENT", TextFinal.SHENDUHULI);
        } else if (position == 5) {
            info.put("TITLE", TextFinal.CHOUYANGXIAODU_TITLE);
            info.put("CONTENT", TextFinal.CHOUYANGXIAODU);
        } else if (position == 6) {
            info.put("TITLE", TextFinal.LIQINGQUCHU_TITLE);
            info.put("CONTENT", TextFinal.LIQINGQUCHU);
        } else if (position == 7) {
            info.put("CONTENT", TextFinal.JINGXI_NEISHI);
        } else if (position == 8) {
            info.put("CONTENT", TextFinal.MONIDALA_NEISHI);
        } else if (position == 9) {
            info.put("CONTENT", TextFinal.SHENDUHULITAOCAN);
        }
        return info;
    }
}
