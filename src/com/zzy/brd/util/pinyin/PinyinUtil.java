/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.pinyin;



import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 
 * @author wwy 2015年5月4日
 */
public class PinyinUtil {
	public static void main(String[] args) {
		String str="大叶榕";
		System.out.println(getPinYin2(str));
	}
	/**
	 * 拼音排序 
	 */
	public static int compare(String o1, String o2) {
		
		int index1 = o1.indexOf("(");
		int index2 = o2.indexOf("(");
		String str1 = o1;
		String str2 = o2;
		if (index1 >= 0){
			str1 = o1.substring(0,index1);
		}
		if (index2 >= 0){
			str2 = o2.substring(0, index2);
		}
		String py1 = getPinyinInitials(str1);
		String py2 = getPinyinInitials(str2);
		return py1.compareTo(py2);
	}
	/**
	 * 获得字符串的拼音
	 * 如"大叶榕"，则输出"dayerong"
	 * @param src
	 * @return
	 */
	public static String getPinYin(String src) { 
        char[] t1 = null; 
        t1 = src.toCharArray();  
        // System.out.println(t1.length); 
        String[] t2 = new String[t1.length]; 
        // System.out.println(t2.length); 
        // 设置汉字拼音输出的格式  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat(); 
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length; 
        try {  
            for (int i =0; i < t0; i++) {  
                // 判断能否为汉字字符  
                // System.out.println(t1[i]); 
               if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) { 
                   t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中 
                    t4 += t2[0]+"";// 取出该汉字全拼的第一种读音并连接到字符串t4后 
               } else { 
                   // 如果不是汉字字符，间接取出字符并连接到字符串t4后 
                    t4 += Character.toString(t1[i]);  
                }  
            }  
       } catch (Exception e) { 
           e.printStackTrace();  
        }  
       return t4;  
    } 
	/**
	 * 获得字符串的拼音
	 * 如"大叶榕"，则输出"da ye rong "
	 * @param src
	 * @return
	 */
	public static String getPinYin2(String src) { 
        char[] t1 = null; 
        t1 = src.toCharArray();  
        // System.out.println(t1.length); 
        String[] t2 = new String[t1.length]; 
        // System.out.println(t2.length); 
        // 设置汉字拼音输出的格式  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat(); 
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length; 
        try {  
            for (int i =0; i < t0; i++) {  
                // 判断能否为汉字字符  
                // System.out.println(t1[i]); 
               if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) { 
                   t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中 
                    t4 += t2[0]+" ";// 取出该汉字全拼的第一种读音并连接到字符串t4后 
               } else { 
                   // 如果不是汉字字符，间接取出字符并连接到字符串t4后 
                    t4 += Character.toString(t1[i]);  
                }  
            }  
       } catch (Exception e) { 
           e.printStackTrace();  
        }  
       return t4;  
    } 
	/**
	 * 获得拼音的首字母
	 */
	public static String getPinyinInitials (String src){
        char[] t1 = null; 
        t1 = src.toCharArray();  
        // System.out.println(t1.length); 
        String[] t2 = new String[t1.length]; 
        // System.out.println(t2.length); 
        // 设置汉字拼音输出的格式  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat(); 
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length; 
        try {  
            for (int i =0; i < t0; i++) {  
                // 判断能否为汉字字符  
                // System.out.println(t1[i]); 
               if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) { 
            	   // 将汉字的几种全拼都存到t2数组中 
            	   t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
            	   // 取出该汉字全拼的第一种读音并连接到字符串t4后 
            	   t4 += t2[0].charAt(0)+"";
               } else { 
                   // 如果不是汉字字符，间接取出字符并连接到字符串t4后 
                    t4 += Character.toString(t1[i]);  
                }  
            }  
       } catch (Exception e) { 
           e.printStackTrace();  
        }  
       return t4;  
	}
	/**
	 * 判断字符串是否都是拼音
	 * @param str
	 * @return
	 */
	public static boolean isPinyin(String str){
		 char[] t1 = null; 
	     t1 = str.toCharArray();
		try {
			for (int i = 0; i < t1.length; i++) {
				// 判断能否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					
				} else {
					// 如果不是汉字字符，返回
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
	     return true;
	}
	/**
	 * 判断是否仅含有英语字母
	 * @param str
	 * @return
	 */
	public static boolean isEnglish(String str){
		if (str.matches("[a-zA-Z]+")){
			return true;
		}
		return false;
		
	}
	/**
	 * 获得查询数据库使用的模糊查询表达式
	 * @param str
	 * @param exp
	 * @return
	 */
	public static String getPYSearchRegExp(String str, String exp) {
		int start = 0;
		String regExp = "";
		str = str.toLowerCase();
		boolean isFirstSpell = true;
		for (int i = 0; i < str.length(); ++i) {
			String tmp = str.substring(start, i + 1);
			isFirstSpell = binSearch(tmp) ? false : true;
			if (isFirstSpell) {
				regExp += str.substring(start, i) + exp;
				start = i;
			} else {
				isFirstSpell = true;
			}

			if (i == str.length() - 1)
				regExp += str.substring(start, i + 1) + exp;
		}
		if (!regExp.startsWith(exp))
			return exp+regExp;
		else
			return regExp;
	}

	/**
	 * 二分搜索
	 * @param str
	 * @return
	 */
	private static boolean binSearch(String str) {
		int start = 0;
		int end = PinyinUtil.pinyin.length - 1;
		int mid = ((end + start) / 2);
		while (start < end && !PinyinUtil.pinyin[mid].matches(str + "[a-zA-Z]*")) {
			if (PinyinUtil.pinyin[mid].compareTo(str) < 0)
				start = mid + 1;
			else
				end = mid - 1;
			mid = ((end + start) / 2);

		}
		if (PinyinUtil.pinyin[mid].matches(str + "[a-zA-Z]*")){
			return true;
		}
		return false;
	}
	public static final String[] pinyin = { "a", "ai",  "an", "ang", "ao", "ba", "bai", "ban", "bang",   
		"bao", "bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin",   
		"bing", "bo", "bu", "ca", "cai", "can", "cang", "cao",  "ce",   
		"ceng", "cha", "chai", "chan", "chang", "chao", "che",  "chen",   
		"cheng", "chi", "chong", "chou", "chu", "chuai", "chuan",
		"chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", 
		"cuan", "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao",   
		"de", "deng", "di", "dian", "diao", "die", "ding", "diu", "dong",   
		"dou", "du", "duan", "dui", "dun", "duo", "e", "en", "er",  "fa",   
		"fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", 
		"gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng",  "gong",   
		"gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo",   
		"ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng",   
		"hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun",  
		"huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing",   
		"jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan",   
		"kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua",    
		"kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan",    
		"lang", "lao", "le", "lei", "leng", "li", "lia", "lian", "liang",   
		"liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv",   
		"luan", "lue", "lun", "luo", "ma", "mai", "man", "mang", "mao",   
		"me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min",    
		"ming", "miu", "mo", "mou", "mu", "na", "nai", "nan", "nang",   
		"nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao",   
		"nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan", "nue",   
		"nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen",   
		"peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu",
		"qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", 
		"qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao",    
		"re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui",   
		"run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen",    
		"seng", "sha", "shai", "shan", "shang", "shao", "she", "shen",    
		"sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang",   
		"shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui",    
		"sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng",
		"ti", "tian", "tiao", "tie", "ting", "tong", "tou",  "tu", "tuan",   
		"tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen",   
		"weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie",   
		"xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya",   
		"yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong",   
		"you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang",   
		"zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan",   
		"zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou",    
		"zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo",   
		"zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo" };
}
