<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="类型名字" prop="name">
      <el-input v-model="dataForm.name" placeholder="类型名字"></el-input>
    </el-form-item>
    <el-form-item label="每分钟消耗多少卡路里" prop="caloriePerMinute">
      <el-input v-model="dataForm.caloriePerMinute" placeholder="每分钟消耗多少卡路里"></el-input>
    </el-form-item>
    <el-form-item label="温馨提示" prop="tip">
      <el-input v-model="dataForm.tip" placeholder="温馨提示"></el-input>
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
          id: 0,
          name: '',
          caloriePerMinute: '',
          tip: ''
        },
        dataRule: {
          name: [
            { required: true, message: '类型名字不能为空', trigger: 'blur' }
          ],
          caloriePerMinute: [
            { required: true, message: '每分钟消耗多少卡路里不能为空', trigger: 'blur' }
          ],
          tip: [
            { required: true, message: '温馨提示不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/sport/type/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.type.name
                this.dataForm.caloriePerMinute = data.type.caloriePerMinute
                this.dataForm.tip = data.type.tip
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
              url: this.$http.adornUrl(`/sport/type/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'caloriePerMinute': this.dataForm.caloriePerMinute,
                'tip': this.dataForm.tip
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
