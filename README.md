# AndroidRemoteDebug
![架构图](README_FILES/arhitecture.png)  
![主流程各方协作](README_FILES/sequence.png)  
引入较为严格的端到端的超时机制，只要出现一次超时就结束debug session。因而两端暂时不需要消息重发机制。
