import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { SidebarService } from '../sidebar/sidebar.service';

@Component({
  selector: 'app-header',
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  constructor(private sidebarService: SidebarService) {}

  toggleSidebar() {
    console.log('PRESSED');
    this.sidebarService.toggle();
  }

  closeSidebar() {
    this.sidebarService.close();
  }
}
