//git凭证ID
def git_auth = "hu-gitee"
//git的url地址
def git_url = "https://gitee.com/moon5672369/hu.git"
//镜像的版本号
def tag = "latest"
//Harbor的url地址
def harbor_url = "114.132.44.209:85"
//镜像库项目名称
def harbor_project = "health"
//Harbor的登录凭证ID
def harbor_auth = "784046bf-7051-4755-8eb4-6f18226df7c0"

node {
   //获取当前选择的项目名称
   def selectedProjectNames = "${project_name}".split(",")

   stage('拉取代码') {
      checkout([$class: 'GitSCM', branches: [[name: "master"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])
   }
   stage('编译，安装公共子工程') {
      sh "mvn -f h-common clean install -Dmaven.test.skip=true"
      sh "mvn -f starter-canal clean install -Dmaven.test.skip=true"
   }
   stage('编译，打包微服务工程，上传镜像') {
          for(int i=0;i<selectedProjectNames.length;i++){
                    //tensquare_eureka_server@10086
                    def projectInfo = selectedProjectNames[i];
                    //当前遍历的项目名称
                    def currentProjectName = "${projectInfo}".split("@")[0]
                    //当前遍历的项目端口
                    def currentProjectPort = "${projectInfo}".split("@")[1]

                    echo "当前遍历的项目名称=》${currentProjectName}"

                    // 1.打包并构建镜像
                    sh "mvn -f ${currentProjectName} clean package -Dmaven.test.skip=true dockerfile:build"

                    //定义镜像名称
                    def imageName = "${currentProjectName}:${tag}"

                    // 2.对镜像打上标签
                    sh "docker tag ${imageName} ${harbor_url}/${harbor_project}/${imageName}"
                    echo "对当前镜像打标签=》 tag ${imageName} ${harbor_url}/${harbor_project}/${imageName}"


                   // 3.把镜像推送到Harbor
                   withCredentials([usernamePassword(credentialsId: "${harbor_auth}", passwordVariable: 'password', usernameVariable: 'username')]) {
                       //登录到Harbor
                       sh "docker login -u ${username} -p ${password} ${harbor_url}"

                       //镜像上传
                       sh "docker push ${harbor_url}/${harbor_project}/${imageName}"
                       echo "上传镜像=》docker push ${harbor_url}/${harbor_project}/${imageName}"

                   }

                  // 运行部署文件
                  echo "运行服务器的部署脚本"
                  sh "/root/deploy.sh ${harbor_url} ${harbor_project} ${currentProjectName} ${tag} ${currentProjectPort}"
           }
      }
}