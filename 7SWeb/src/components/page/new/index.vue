<template>
    <div>
        <div>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="部门">
                    <el-select v-model="depId" clearable placeholder="请选择" @change="selectDep">
                        <el-option
                            v-for="item in department"
                            :key="item.id"
                            :label="item.depName"
                            :value="item.id"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="班别" v-show="showClass">
                    <el-select v-model="classId" clearable placeholder="请选择">
                        <el-option
                            v-for="item in classes"
                            :key="item.depSecendId"
                            :label="item.depSecendName"
                            :value="item.depSecendId"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="日期">
                    <el-date-picker
                        v-model="dates"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="timestamp"
                    ></el-date-picker>
                </el-form-item>
                <el-button type="primary" @click="selectItems">查询</el-button>
            </el-form>
        </div>
        <div>
            <el-table :data="tableData" style="width: 100%">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="商品名称">
                                <span>{{ props.row.deduct.reason }}</span>
                            </el-form-item>
                            <el-form-item label="所属店铺">
                                <span>{{ props.row.shop }}</span>
                            </el-form-item>
                            <el-form-item label="商品 ID">
                                <span>{{ props.row.id }}</span>
                            </el-form-item>
                            <el-form-item label="店铺 ID">
                                <span>{{ props.row.shopId }}</span>
                            </el-form-item>
                            <el-form-item label="商品分类">
                                <span>{{ props.row.category }}</span>
                            </el-form-item>
                            <el-form-item label="店铺地址">
                                <span>{{ props.row.address }}</span>
                            </el-form-item>
                            <el-form-item label="商品描述">
                                <span>{{ props.row.desc }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column label="序号" prop="id"></el-table-column>
                <el-table-column label="点检项目" prop="item"></el-table-column>
                <el-table-column label="分数" prop="score"></el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            department: [],
            classes: [],
            depId: '',
            classId: '',
            dates: '',
            showClass: false,
            tableData: [
                
            ]
        };
    },
    methods: {
        selectDep() {
            console.log(this.depId);
            let that = this;
            this.axios.get('api/getSecend?depId=' + this.depId).then((res) => {
                console.log(res.data);
                if (res.data.length > 0) {
                    that.showClass = true;
                    that.classes = res.data;
                } else {
                    that.showClass = false;
                }
            });
        },
        selectItems() {
            var that=this;
            var params={
                "startTime":that.dates
            }
            console.log(this.depId + '-' + this.classId + '-' + this.dates);
            this.axios.get("api/getDeduct?dates="+that.dates+"&depId="+that.depId+"&depSecendId="+that.classId).then(
                res => {
                    console.log(res.data);
                    that.tableData=res.data;
                }

            )
        }
    },
    created() {
        let that = this;
        this.axios.get('api/getDep').then((res) => {
            console.log(res.data);
            that.department = res.data;
            
        });
    }
};
</script>


<style scoped>
</style>

