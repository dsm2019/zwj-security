<template>
  <div class="mod-dnf__trend">
    <div style="display: flex; justify-content: space-between; align-items: center">
      <el-form :inline="true" :model="state.dataForm">
        <el-form-item>
          <el-select v-model="state.dataForm.attributeName" placeholder="选择属性" @change="state.getDataList()">
            <el-option label="名望" value="fame"></el-option>
            <el-option label="名望No." value="fameRanking"></el-option>
            <el-option label="模拟伤害" value="simulatedDamage"></el-option>
            <el-option label="模拟伤害No." value="simulatedDamageRanking"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-date-picker v-model="state.dataForm.dateRange" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="state.getDataList()"></el-date-picker>
        </el-form-item>
      </el-form>
      <div style="display: flex; gap: 10px">
        <el-button @click="state.toggleCheckboxes">{{ state.showCheckboxes ? "隐藏" : "选择" }}</el-button>
      </div>
      <el-button @click="state.toggleDisplayTable">
        {{ state.displayTable ? "隐藏表格" : "显示表格" }}
      </el-button>
      <el-button @click="state.toggleDisplayLine">
        {{ state.displayLine ? "隐藏折线" : "显示折线" }}
      </el-button>
      <el-button @click="state.toggleDisplayBar">
        {{ state.displayBar ? "隐藏柱形图" : "显示柱形图" }}
      </el-button>
    </div>
    <div v-if="state.displayTable">
      <el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column v-if="state.showCheckboxes" type="selection" width="55"></el-table-column>
        <el-table-column prop="name" label="角色" header-align="center" align="center"></el-table-column>
        <el-table-column v-for="date in state.dates" :key="date" :label="date" header-align="center" align="center">
          <template #default="scope">
            {{ getValue(scope.row, date) }}
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-if="state.displayLine">
      <div ref="chartRef" style="width: 100%; height: 400px"></div>
    </div>
    <div v-if="state.displayBar">
      <div ref="barChartRef" style="width: 800px; height: 400px"></div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, onMounted, ref, watch, nextTick, onUnmounted } from "vue";
import baseService from "@/service/baseService";
import * as echarts from "echarts";

// 定义接口类型
interface SubItem {
  date: string;
  value: number;
}

interface CharacterData {
  characterId: string;
  name: string;
  characterAvatar: string;
  data: SubItem[];
}

interface ResponseData {
  code: number;
  msg: string;
  data: CharacterData[];
}

// 移除 selectedColumns 相关逻辑
const state = reactive({
  dataForm: {
    attributeName: "fame",
    dateRange: [] as Date[] // 添加日期范围字段
  },
  dataList: [] as { [key: string]: number | string }[],
  dataListLoading: false,
  dates: [] as string[],
  displayTable: true,
  displayLine: false,
  displayBar: false,
  chartOptions: {},
  selectedRows: [] as any[],
  showCheckboxes: false, // 控制勾选框的显示与隐藏
  getDataList() {
    this.dataListLoading = true;
    let url = `/dnf/history-attribute/trend?attributeName=${this.dataForm.attributeName}`;
    if (this.dataForm.dateRange && this.dataForm.dateRange.length === 2) {
      const formatDate = (date: Date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");
        return `${year}-${month}-${day}`;
      };
      const from = formatDate(this.dataForm.dateRange[0]);
      const to = formatDate(this.dataForm.dateRange[1]);
      url += `&from=${from}&to=${to}`;
    }
    baseService
      .get(url)
      .then((res: ResponseData) => {
        if (res.code === 0) {
          const allDates = new Set();
          this.dataList = res.data.map((item: CharacterData) => {
            const rowData: { [key: string]: number | string } = { name: item.name };
            item.data.forEach((subItem: SubItem) => {
              rowData[subItem.date] = subItem.value;
              allDates.add(subItem.date);
            });
            return rowData;
          });
          this.dates = Array.from(allDates).sort() as string[];
          // 默认全选所有行
          this.selectedRows = [...this.dataList];
          this.updateChartOptions();
        }
        this.dataListLoading = false;
      })
      .catch(() => {
        this.dataListLoading = false;
      });
  },
  toggleDisplayTable() {
    state.displayTable = !state.displayTable;
  },
  toggleDisplayLine() {
    state.displayLine = !state.displayLine;
    if (state.displayLine) {
      nextTick(() => {
        chart?.dispose();
        initChart();
      });
    }
  },
  toggleDisplayBar() {
    state.displayBar = !state.displayBar;
    if (state.displayBar) {
      nextTick(() => {
        barChart?.dispose();
        initZhuChart();
      });
    }
  },
  toggleCheckboxes() {
    state.showCheckboxes = !state.showCheckboxes;
  },
  updateChartOptions() {
    const selectedRows = this.selectedRows;
    const labels = this.dates;
    const datasets = selectedRows.map((row) => {
      const data = labels.map((date) => {
        const value = getValue(row, date);
        return typeof value === "number" ? value : null;
      });
      return {
        type: "line",
        name: row.name as string, // 添加 name 属性，用于图例显示
        data: data,
        borderColor: `#${Math.floor(Math.random() * 16777215).toString(16)}`,
        fill: false
      };
    });

    this.chartOptions = {
      // 添加 legend 组件
      legend: {
        type: "plain",
        bottom: 0,
        data: datasets.map((item) => item.name),
        textStyle: {
          color: "#333"
        },
        pageIconColor: "#333",
        pageIconInactiveColor: "#aaa",
        pageTextStyle: {
          color: "#333"
        }
      },
      xAxis: {
        type: "category",
        data: labels
      },
      yAxis: {
        type: "value"
      },
      series: datasets
    };
  }
});

const chartRef = ref(null);
const barChartRef = ref(null);
let chart: echarts.ECharts | null = null;
let barChart: echarts.ECharts | null = null;

// 修改后的插值计算函数
const getValue = (row: { [key: string]: number | string }, date: string) => {
  if (row[date] !== undefined) {
    return row[date];
  }
  const dateIndex = state.dates.indexOf(date);
  let prevIndex = dateIndex - 1;

  // 向前查找最近的有值日期
  while (prevIndex >= 0 && row[state.dates[prevIndex]] === undefined) {
    prevIndex--;
  }

  if (prevIndex >= 0) {
    return row[state.dates[prevIndex]];
  }

  return "N/A";
};

const initChart = () => {
  if (chartRef.value) {
    chart = echarts.init(chartRef.value);
    chart.setOption(state.chartOptions);
  }
};

let intervalId: ReturnType<typeof setInterval> | null = null;

const initZhuChart = () => {
  if (!barChartRef.value) return;
  const myChart = echarts.init(barChartRef.value);

  const options: { [name: string]: number[] } = {};
  // 处理每个角色数据
  state.dataList.forEach((item) => {
    const name = item.name as string;
    const values: number[] = [];

    // 遍历对象的所有属性，提取日期对应的数值
    for (const key in item) {
      if (key !== "name" && typeof item[key] === "number") {
        values.push(item[key] as number);
      }
    }

    // 按日期排序（假设属性名格式为 YYYY-MM-DD）
    values.sort((a, b) => {
      // 这里简化处理，实际应根据日期字符串排序
      // 由于示例中未提供完整日期，暂不实现具体排序逻辑
      return 0;
    });

    options[name] = values;
  });

  const characters = Object.keys(options);

  // 修正标题与实际数据年份相符
  const option: echarts.EChartsOption = {
    title: {
      text: `${state.dates[0]}`
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow"
      }
    },
    legend: {
      data: characters
    },
    // 修改 xAxis 为数值轴
    xAxis: {
      type: "value"
    },
    // 修改 yAxis 为类目轴
    yAxis: {
      type: "category",
      data: characters,
      axisTick: {
        alignWithLabel: true
      },
      // 隐藏 y 轴标签
      axisLabel: {
        show: false
      }
    },
    series: characters.map((character) => ({
      name: character,
      type: "bar",
      data: [options[character][0]],
      // 添加动画配置
      animationDuration: 1000,
      animationEasing: "cubicOut",
      // 添加 label 配置
      label: {
        show: true,
        position: 'right', // 将标签放在柱子右侧（顶部）
        formatter: character // 显示角色名
      }
    })),
    // 全局动画配置
    animation: true,
    animationDuration: 1000,
    animationEasing: "cubicOut"
  };

  myChart.setOption(option);

  let currentDateIndex = 0;
  const updateChart = () => {
    currentDateIndex = (currentDateIndex + 1) % state.dates.length;
    const newSeries = characters.map((character) => ({
      name: character,
      type: "bar",
      data: [options[character][currentDateIndex]],
      animationDuration: 1000,
      animationEasing: "cubicOut",
      label: {
        show: true,
        position: 'right',
        formatter:  character
      }
    }));

    myChart.setOption({
      series: newSeries,
      title: {
        text: `${state.dates[currentDateIndex]}`
      }
    });
  };

  intervalId = setInterval(updateChart, 2000);

  const cleanup = () => {
    if (intervalId) {
      clearInterval(intervalId);
    }
    myChart.dispose();
  };

  onUnmounted(cleanup);
};

watch(
  () => state.chartOptions,
  () => {
    if (chart) {
      chart.setOption(state.chartOptions);
    }
  },
  { deep: true }
);

// 组件挂载时调用 getDataList 方法
onMounted(() => {
  state.getDataList();
});

// 在表格选择变化时更新图表
const tableRef = ref(null);
watch(
  () => state.selectedRows,
  () => {
    state.updateChartOptions();
  },
  { deep: true }
);

const handleSelectionChange = (val) => {
  state.selectedRows = val;
};
</script>
