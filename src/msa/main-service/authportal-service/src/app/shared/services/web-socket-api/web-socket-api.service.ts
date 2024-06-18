import * as SockJS from 'sockjs-client';
import {Stomp} from '@stomp/stompjs';
import {environment} from '../../../../environments/environment';
import {Injectable} from '@angular/core';
import {PrivilegeConst} from '../../constants/PrivilegeConst';
import {NbToastrService} from '@nebular/theme';
import {ChatComponent} from '../../../pages/category/chat/chat.component';
import {HeaderComponent} from '../../../@theme/components';

@Injectable({
  providedIn: 'root'
})

export class WebSocketAPI {
  baseUrl = `${environment.API_ENDPOINT}`;
  webSocketEndPoint: string = `${this.baseUrl}/ws`;
  topic: string = "/topic/chat";
  topicUser: string = "/user/specific";
  stompClient: any;

  constructor(private chatComponent?: ChatComponent,
              private headerComponent?: HeaderComponent
  ) {
  }

  _connect() {
    // console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({}, function (frame) {
      _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
        _this.onMessageReceived(sdkEvent);
      });
      //_this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
  };

  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log("Disconnected");
  }

  errorCallBack(error) {
    setTimeout(() => {
      console.log("Reconnect");
      this._connect();
    }, 5000);
  }

  _send(message) {
    this.stompClient.send("/app/chat", {}, JSON.stringify(message));
  }

  onMessageReceived(message) {
    this.headerComponent.handleNotifyWebSocket(message.body);
  }

  /////////////////////////////////////////////////////////////
  onMessageReceivedCustom(message) {
    // console.log("Message Recieved from Server :: " + message);
    if (this.headerComponent) {
      this.headerComponent.handleNotifyWebSocket(message.body);
    } else {
      this.chatComponent.handleMessage(message.body);
    }
  }

  _connectUser() {
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({}, function (frame) {
      _this.stompClient.subscribe(_this.topicUser, function (sdkEvent) {
        _this.onMessageReceived(sdkEvent);
      });
      //_this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
  };

  _sendUser(message) {
    // console.log("calling logout api via web socket");
    this.stompClient.send("/user/specific", {}, JSON.stringify(message));
  }
}
