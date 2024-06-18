import {Component, OnInit} from '@angular/core';
import {WebSocketAPI} from '../../../shared/services/web-socket-api/web-socket-api.service';
import {NbToastrService} from '@nebular/theme';
import {Stomp} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'ngx-chat',
  templateUrl: './chat.component.html'
})

export class ChatComponent implements OnInit {

  webSocketAPI: WebSocketAPI;
  greetings: string[] = [];
  disabled = true;
  newmessage: string;
  chatComponent: ChatComponent;
  connected: boolean = false;

  private toastrService: NbToastrService

  constructor(toastrService: NbToastrService) {
    this.toastrService = toastrService;
  }

  ngOnInit(): void {
    this.webSocketAPI = new WebSocketAPI(this, null);
    this.connect();
  }

  connect() {
    this.webSocketAPI._connectUser();
    this.connected = true;
  }

  disconnect() {
    this.webSocketAPI._disconnect();
    this.connected = false;
  }

  sendMessage() {
    this.webSocketAPI._sendUser(this.newmessage);
    this.newmessage = "";
  }

  handleMessage(message) {
    this.greetings.push(message);
    this.toastrService.info(message, 'Chat');
  }

}
