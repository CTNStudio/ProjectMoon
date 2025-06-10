package ctn.project_moon.capability.item;

import ctn.project_moon.api.FourColorAttribute;
import net.minecraft.network.chat.Component;

import java.util.List;

/// 颜色伤害要求
public interface IColorUsageReqItem {
	List<Integer> getFortitudeUsageReq();
	List<Integer> getPrudenceUsageReq();
	List<Integer> getTemperanceUsageReq();
	List<Integer> getJusticeUsageReq();
	List<Integer> getCompositeRatingUsageReq();

	Component getToTooltip();
	Component analysisUsageReq(FourColorAttribute.Type attribute, boolean detailed);

	/**
	 * 判断属性值是否符合使用条件
	 * @param attribute 来源属性
	 * @param value 属性值
	 * @return 是否符合使用条件
	 */
	boolean isAccord(FourColorAttribute.Type attribute, int value);
}
