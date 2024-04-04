## 通用的BaseDialog库
> 1. 继承于DialogFragment实现
> 2. 由于Dialog和DialogFragment都不能动态设置宽高,因此仅适用于已知布局宽高的场景
> 3. 如果需要使用LoadingDialog,建议完全自定义View实现,更符合不同状态和场景
> 4. 建议在外部单独设置 dialog.setCancelable(false); / dialog.setCanceledOnTouchOutside(false); 因为这两个个参数会互相影响
 
LoadingDialog參考:
https://github.com/Kaopiz/KProgressHUD
https://github.com/ForgetAll/LoadingDialog
