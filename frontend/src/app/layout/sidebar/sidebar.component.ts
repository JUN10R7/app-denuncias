import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { gsap } from 'gsap';
import { SidebarService } from './sidebar.service';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent {
  role: string = '';
  isOpen: boolean = false;
  screenIsSmall = false;

  constructor(
    private auth: AuthService,
    private sidebarService: SidebarService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.role = this.auth.getRole() || '';
    this.sidebarService.isOpen$.subscribe((value) => {
      this.isOpen = value;
    });

    this.screenIsSmall = window.innerWidth < 768;

    this.router.events.subscribe((event) => {
      if (this.screenIsSmall) {
        this.sidebarService.close();
      }
    });

    window.addEventListener('resize', () => {
      this.screenIsSmall = window.innerWidth < 768;
      if (!this.screenIsSmall) {
        this.sidebarService.open();
      } else {
        this.sidebarService.close();
      }
    });
  }

  logout(): void {
    this.auth.logout();
  }

  toggleDark() {
    const classList = document.documentElement.classList;
    const isDark = classList.contains('dark');

    if (isDark) {
      classList.remove('dark');
      localStorage.setItem('theme', 'light');
    } else {
      classList.add('dark');
      localStorage.setItem('theme', 'dark');
    }
  }
}
