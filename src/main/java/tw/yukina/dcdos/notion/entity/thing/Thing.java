package tw.yukina.dcdos.notion.entity.thing;

import lombok.Builder;
import lombok.Data;
import tw.yukina.dcdos.notion.validate.NotionDate;
import tw.yukina.dcdos.notion.validate.NotionTime;
import tw.yukina.dcdos.notion.validate.ProjectId;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Thing {

    @NotBlank
    private String title;

    @ThingStatus
    private String status;

    @ThingTags
    private String[] tags;

    private String projectTags;

    private boolean isEvent = false;

    private boolean deadLineTimeEnable = false;

    @NotionDate
    private String deadLineStartDate;

    @NotionDate
    private String deadLineEndDate;

    @NotionTime
    private String deadLineStartTime;

    @NotionTime
    private String deadLineEndTime;

    @ProjectId
    private String project;

    private String note;

    private String code;

    public static String[] getAllValidTags() {
        return new String[]{
                "重要", "計畫", "行政", "學習", "創意", "溝通", "寄信",
                "關注", "課程", "作業", "檢討", "coding", "meeting", "報告",
                "時間點", "放假", "考試", "重要考試", "考試參考日", "San 值注意",
                "可能的社交場合", "普通日期", "申請相關", "活動日期", "等待開始",
                "等待更新", "等待回覆", "部分完成並更新", "需求消失", "進行中",
                "Close", "完成", "失敗", "阻塞中（不需催促）", "阻塞中（需催促）",
                "例外", "Project", "Listener", "Milestone", "參考用，需確認",
                "議程", "一顆蕃茄", "兩顆蕃茄", "三顆蕃茄", "四顆蕃茄",
                "五顆蕃茄", "六顆蕃茄"
        };
    }

    public static String[] getAllValidStatus() {
        return new String[]{
                "Buffer", "New", "Waiting for", "Next action", "Close"
        };
    }
}