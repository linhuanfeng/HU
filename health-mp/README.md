# H-U 医芽

- 技术栈：vue3(setup-script)+vite2+uniapp。

- 使用 eslint+prettier+gitHooks 格式和校验代码,提高代码规范性和开发效率

- 使用先进的包管理工具 pnpm。

提示：uview-ui: "2.0.27"(newest), 目前还不支持 vue3，该项目组件使用 uni-ui。

### 项目基本结构

```json
├── common // 常用css样式
├── components // 自定义组件(复用)
├── pages // 页面组件模块
│ └── index // 页面名称
│ └── index.vue // 页面组件
├── static // 静态资源
│ ├── images
│ ├── tabbar
│ ├── other
| └── xxx
├── store // pinia状态管理
| ├── xxx.js
| └── xxx.js
│
├── utils // 请求封装
├── App.vue // 根组件
├── main.js // 入口文件
├── manifest.json // 小程序配置文件
├── pages.json // 小程序配置文件
└── uni.scss // uni官方统一样式
```

### 开发指南

1. 该项目 node 版本——16.13.2(使用 nvm 进行 node 版本控制)

   > - windows：[使用 nvm 管理 node 版本，一条龙解决前端开发环境配置 - 掘金](https://juejin.cn/post/7011398696999288839)
   > - mac：[Mac OS 下 NVM 的安装与使用 - 简书](https://www.jianshu.com/p/622ad36ee020)

2. 推荐 vscode 插件

   > vetur、Vue VSCode Snippets（vue 代码片段）、eslint、prettier 等。
   >
   > 两篇推荐文章，根据实际情况选择安装即可
   >
   > - [2021 精选 15+VSCode 插件推荐 - 掘金](https://juejin.cn/post/7022982794338893854)
   > - [【利器篇】前端 40+精选 VSCode 插件，总有几个你未拥有！ - 掘金](https://juejin.cn/post/6997186741866070023)

   可能需要更改 vscode 配置：

   入口：左下角设置，点击命令面板。输入 setting，点击“首选项，打开设置“，将下面代码拷贝进{}中：

   ```json
      "editor.formatOnSave": true,
      "editor.defaultFormatter": "esbenp.prettier-vscode",
      "editor.codeActionsOnSave": {
       "source.fixAll.eslint": true
      }
   ```

3. 常用开发命令

```bash
# pnpm address https://pnpm.io/zh/motivation
# 安装依赖(建议用pnpm)
# 你可以使用 "npm -g i pnpm" 去安装pnpm
pnpm i

# 启动h5
pnpm run dev:h5

# 启动 微信
pnpm run dev:mp-weixin
```

4. 其他（可在 package.json 查看）

```bash
# 代码格式检查并自动修复
pnpm run lint
```

### **代码风格**

vue3 风格指南（推荐看） -> https://v3.cn.vuejs.org/style-guide

### git 提交规范

```
<type>(<scope>): <subject>
// 注意冒号 : 后有空格
// 如 feat(miniprogram): 增加了小程序模板消息相关功能
复制代码
```

**scope 选填**表示 commit 的作用范围，如数据层、视图层，也可以是目录名称  **subject 必填**用于对 commit 进行简短的描述  **type 必填**表示提交类型，值有以下几种：

- feat - 新功能 feature
- fix - 修复 bug
- docs - 文档注释
- style - 代码格式(不影响代码运行的变动)
- refactor - 重构、优化(既不增加新功能，也不是修复 bug)
- perf - 性能优化
- test - 增加测试
- chore - 构建过程或辅助工具的变动
- revert - 回退
- build - 打包

###

### 案例

1. getter 使用

   ```javascript
   import { useStore } from 'vuex';
   let getterValue = ref(null);
   const getterFunc = () => {
     getterValue.value = store.getters.cachedViews;
   };
   // vue页面中
   {
     {
       store.getters.getDay;
     }
   }
   ```

2. request 请求例子

   ```javascript
   import uniRequest from '@/utils/uniRequest';
   const testReq = () => {
    let reqConfig = {
    url: '/integration-front/user/loginOut', //依据实际API write url
    method: 'post',
    };
    uniRequest(reqConfig).then((res) => {});
   };

   // get例子
   import uniRequest from '@/utils/uniRequest';
   const testReq = () => {
     uniRequest({
       url: '/portal/1477994686370156544',
       method: 'get',
       // isSBLoading: true,
       // isHALoading: true,
       // data: {
       //     test: 1,
       // },
       // isParams: true,
    }).then((res) => {
       console.log('@请求成功:', res);
   });
   ```

3. mutations 事件触发

   ```javascript
   import { useStore } from 'vuex';
   const store = useStore();
   //store获取
   const changeStore = () => {
     store.commit('shopping/setDay', '2022.2.14');
   };
   ```

4. 页面跳转（带参数）

   ```javascript
   // 跳转页
   import { getCurrentInstance } from 'vue';
   const { proxy } = getCurrentInstance();
   const toNavigateOne = () => {
     let data = {
       licenseNo: 11,
       name: 'gulugul',
     };
     proxy.toNavigatePageMixin('/pages/navigateOne/navigateOne', data);
   };

   // 被跳转页接受参数
   import { onMounted, getCurrentInstance } from 'vue';
   // 此处可以获得页面传递过来的参数
   let { proxy } = getCurrentInstance();
   onMounted(() => {
     console.log('proxy.paramsMixin', proxy.paramsMixin);
   });
   ```

5. 接收组件传递的信息

   ```javascript
   const props = defineProps({
     name: {
       require: true,
       default: 'fai',
       type: String,
     },
   });
   ```

6. setup 中数据导出

   ```javascript
   const data = reactive({
     levelList: null,
   });
   // 导出属性到页面中使用;
   let { levelList } = toRefs(state);
   ```
