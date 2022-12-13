<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户编号" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户编号"></el-input>
    </el-form-item>
    <el-form-item label="每日计划时长" prop="planPerDay">
      <el-input v-model="dataForm.planPerDay" placeholder="每日计划时长"></el-input>
    </el-form-item>
    <el-form-item label="每周计划时长" prop="planPerWeek">
      <el-input v-model="dataForm.planPerWeek" placeholder="每周计划时长"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          planId: 0,
          userId: '',
          planPerDay: '',
          planPerWeek: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '用户编号不能为空', trigger: 'blur' }
          ],
          planPerDay: [
            { required: true, message: '每日计划时长不能为空', trigger: 'blur' }
          ],
          planPerWeek: [
            { required: true, message: '每周计划时长不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.planId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.planId) {
            this.$http({
              url: this.$http.adornUrl(`/sport/plan/info/${this.dataForm.planId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.plan.userId
                this.dataForm.planPerDay = data.plan.planPerDay
                this.dataForm.planPerWeek = data.plan.planPerWeek
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/sport/plan/${!this.dataForm.planId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'planId': this.dataForm.planId || undefined,
                'userId': this.dataForm.userId,
                'planPerDay': this.dataForm.planPerDay,
                'planPerWeek': this.dataForm.planPerWeek
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
