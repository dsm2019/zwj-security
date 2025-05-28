package io.renren.modules.dnf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 地下城表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-05-23
 */
@Data
@Schema(name = "地下城表")
public class DnfCompletionRecordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "ID")
	private Long id;

	@SchemaProperty(name = "地下城ID")
	private Long dungeonId;

	@SchemaProperty(name = "角色ID")
	private Long characterId;

	@SchemaProperty(name = "ID")
	private String completionPeriod;

	@SchemaProperty(name = "完成的时间(毫秒)")
	private Integer completiontime;

	@SchemaProperty(name = "创建者")
	private Long creator;

	@SchemaProperty(name = "创建时间")
	private Date createDate;

	@SchemaProperty(name = "更新者")
	private Long updater;

	@SchemaProperty(name = "更新时间")
	private Date updateDate;


}
