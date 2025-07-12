import { Component, HostListener } from '@angular/core';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HeaderComponent } from './header/header.component';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SidebarService } from './sidebar/sidebar.service';

@Component({
  selector: 'app-layout.component',
  imports: [SidebarComponent, HeaderComponent, RouterOutlet, CommonModule],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {
isSidebarOpen = false;
  screenIsSmall = false;

  constructor(private sidebarService: SidebarService) {}

  ngOnInit(): void {
    this.sidebarService.isOpen$.subscribe((isOpen) => {
      this.isSidebarOpen = isOpen;
    });

    this.updateScreenSize();
  }

  @HostListener('window:resize')
  onResize() {
    this.updateScreenSize();
  }

  updateScreenSize() {
    this.screenIsSmall = window.innerWidth < 768;
  }

  closeSidebar() {
    this.sidebarService.close();
  }

}
