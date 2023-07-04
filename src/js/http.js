import axios from 'axios';
// 设置响应拦截器
axios.interceptors.response.use(
    response => {
        console.log("成功")
        // 处理成功的响应
        return response;

    },
    error => {
        console.log("失败")
        // 处理失败的响应
        const { response } = error;
        if (response) {
            error.data = response.data;
            error.status = response.status;
            error.statusText = response.statusText;
        } else {
            error.data = {
                msg: '网络错误',
                data: null
            };
            error.status = 0;
            error.statusText = '网络错误';
        }
        // 返回一个被拒绝的Promise对象，用于向后传递错误
        return Promise.reject(error);
    }
);

export default axios;
