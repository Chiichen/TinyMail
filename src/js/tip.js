import { createApp, defineComponent,createVNode,render  } from 'vue';
import mobileTip from "../components/mobileTip.vue";

const mobileTipComponent = defineComponent(mobileTip);


// const vnode = createVNode('div', {
//     class: 'wrapper',
//     style: { color: 'red' }
// }, [
//     createVNode('h1', {}, 'Hello, Vue 3!'),
//     createVNode('p', {}, 'This is a paragraph.')
// ]);
//
// render(vnode, document.body);
const divVNode=createVNode('div',{class: 'wrapper',id:'#tip'})
render(divVNode, document.body)
const div = divVNode.el
// console.log('虚拟DOM', divVNode)
// console.log('真实DOM', div)

// const div = document.createElement('div')
// div.setAttribute('class', 'xtx-message-container')
// document.body.appendChild(div)

export function mobileTipjs(option) {
    // const app = createApp(mobileTipComponent, option);
    // const instance = app.mount('#app');
    //
    // document.body.appendChild(instance.$el);
    // console.log("1")
    // setTimeout(() => {
    //     if (instance.$el) instance.$el.remove();
    //     console.log("2")
    // }, 3000);

    const comVNode = createVNode(mobileTip, option)
    console.log("1")
    // document.body.appendChild(div)
    render(comVNode, div)
    // render(comVNode, div)
    // 3.提示在 3s 后自动卸载
    setTimeout(() => {
        render(null, div)
    }, 3000)

    // document.body.appendChild(instance.$el);
    // setTimeout(() => {
    //     if (instance.$el) instance.$el.remove();
    // }, 3000);




}

// ['success', 'info', 'error'].forEach(type => {
//     mobileTipjs[type] = options => {
//         if (typeof options === 'string') {
//             options = {
//                 msg: options
//             };
//         }
//         options.type = type;
//         return mobileTipjs(options);
//     };
// });
