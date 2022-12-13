<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="parentCid">
      <el-input v-model="dataForm.parentCid" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="name">
      <el-input v-model="dataForm.name" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="catLevel">
      <el-input v-model="dataForm.catLevel" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="showStatus">
      <el-input v-model="dataForm.showStatus" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="sort">
      <el-input v-model="dataForm.sort" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="icon">
      <el-input v-model="dataForm.icon" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="relatedIntroduction">
      <el-input v-model="dataForm.relatedIntroduction" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="symptom">
      <el-input v-model="dataForm.symptom" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="preventiveMethods">
      <el-input v-model="dataForm.preventiveMethods" placeholder=""></el-input>
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
          catId: 0,
          parentCid: '',
          name: '',
          catLevel: '',
          showStatus: '',
          sort: '',
          icon: '',
          relatedIntroduction: '',
          symptom: '',
          preventiveMethods: ''
        },
        dataRule: {
          parentCid: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          catLevel: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          showStatus: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          relatedIntroduction: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          symptom: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          preventiveMethods: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.catId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.catId) {
            this.$http({
              url: this.$http.adornUrl(`/selfdiagonsis/selfdiagonsissymptom/info/${this.dataForm.catId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.parentCid = data.selfdiagonsisSymptom.parentCid
                this.dataForm.name = data.selfdiagonsisSymptom.name
                this.dataForm.catLevel = data.selfdiagonsisSymptom.catLevel
                this.dataForm.showStatus = data.selfdiagonsisSymptom.showStatus
                this.dataForm.sort = data.selfdiagonsisSymptom.sort
                this.dataForm.icon = data.selfdiagonsisSymptom.icon
                this.dataForm.relatedIntroduction = data.selfdiagonsisSymptom.relatedIntroduction
                this.dataForm.symptom = data.selfdiagonsisSymptom.symptom
                this.dataForm.preventiveMethods = data.selfdiagonsisSymptom.preventiveMethods
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
              url: this.$http.adornUrl(`/selfdiagonsis/selfdiagonsissymptom/${!this.dataForm.catId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'catId': this.dataForm.catId || undefined,
                'parentCid': this.dataForm.parentCid,
                'name': this.dataForm.name,
                'catLevel': this.dataForm.catLevel,
                'showStatus': this.dataForm.showStatus,
                'sort': this.dataForm.sort,
                'icon': this.dataForm.icon,
                'relatedIntroduction': this.dataForm.relatedIntroduction,
                'symptom': this.dataForm.symptom,
                'preventiveMethods': this.dataForm.preventiveMethods
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
