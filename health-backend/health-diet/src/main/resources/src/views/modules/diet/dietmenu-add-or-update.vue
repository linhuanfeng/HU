<template>
  <el-dialog
    :title="!dataForm.catId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="分类名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="分类名称"></el-input>
    </el-form-item>
    <el-form-item label="父分类" prop="parentCid">
      <el-input v-model="dataForm.parentCid" placeholder="父分类"></el-input>
    </el-form-item>
    <el-form-item label="层级" prop="catLevel">
      <el-input v-model="dataForm.catLevel" placeholder="层级"></el-input>
    </el-form-item>
    <el-form-item label="是否显示" prop="showStatus">
      <el-input v-model="dataForm.showStatus" placeholder="是否显示"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="图标地址" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="图标地址"></el-input>
    </el-form-item>
    <el-form-item label="" prop="quantityOfHeat">
      <el-input v-model="dataForm.quantityOfHeat" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="carbonWaterRatio">
      <el-input v-model="dataForm.carbonWaterRatio" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="proportionOfFat">
      <el-input v-model="dataForm.proportionOfFat" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="proportionOfProtein">
      <el-input v-model="dataForm.proportionOfProtein" placeholder=""></el-input>
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
          name: '',
          parentCid: '',
          catLevel: '',
          showStatus: '',
          sort: '',
          icon: '',
          quantityOfHeat: '',
          carbonWaterRatio: '',
          proportionOfFat: '',
          proportionOfProtein: ''
        },
        dataRule: {
          name: [
            { required: true, message: '分类名称不能为空', trigger: 'blur' }
          ],
          parentCid: [
            { required: true, message: '父分类不能为空', trigger: 'blur' }
          ],
          catLevel: [
            { required: true, message: '层级不能为空', trigger: 'blur' }
          ],
          showStatus: [
            { required: true, message: '是否显示不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '图标地址不能为空', trigger: 'blur' }
          ],
          quantityOfHeat: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          carbonWaterRatio: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          proportionOfFat: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          proportionOfProtein: [
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
              url: this.$http.adornUrl(`/diet/dietmenu/info/${this.dataForm.catId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.dietMenu.name
                this.dataForm.parentCid = data.dietMenu.parentCid
                this.dataForm.catLevel = data.dietMenu.catLevel
                this.dataForm.showStatus = data.dietMenu.showStatus
                this.dataForm.sort = data.dietMenu.sort
                this.dataForm.icon = data.dietMenu.icon
                this.dataForm.quantityOfHeat = data.dietMenu.quantityOfHeat
                this.dataForm.carbonWaterRatio = data.dietMenu.carbonWaterRatio
                this.dataForm.proportionOfFat = data.dietMenu.proportionOfFat
                this.dataForm.proportionOfProtein = data.dietMenu.proportionOfProtein
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
              url: this.$http.adornUrl(`/diet/dietmenu/${!this.dataForm.catId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'catId': this.dataForm.catId || undefined,
                'name': this.dataForm.name,
                'parentCid': this.dataForm.parentCid,
                'catLevel': this.dataForm.catLevel,
                'showStatus': this.dataForm.showStatus,
                'sort': this.dataForm.sort,
                'icon': this.dataForm.icon,
                'quantityOfHeat': this.dataForm.quantityOfHeat,
                'carbonWaterRatio': this.dataForm.carbonWaterRatio,
                'proportionOfFat': this.dataForm.proportionOfFat,
                'proportionOfProtein': this.dataForm.proportionOfProtein
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
