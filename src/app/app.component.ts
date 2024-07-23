import {Component, ViewChild} from '@angular/core';
import { ActivatedRoute, NavigationEnd, RouterOutlet, Event as NavigationEvent } from '@angular/router';
import {HeaderComponent} from "./components/header/header.component";
import {NavbarComponent} from "./components/navbar/navbar.component";
import {FooterComponent} from "./components/footer/footer.component";
import {SidebarComponent} from "./components/sidebar/sidebar.component";
import { Router } from 'express'; 
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, NavbarComponent, FooterComponent, SidebarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  showSidebarAndNavbar: boolean = true;
  title = 'RoyalStock';
  
  @ViewChild('sidebar') sidebar: SidebarComponent | null = null;

  // constructor(private router: Router) {
  //   this.router.events.subscribe((event: Event) => {
  //     if (event instanceof NavigationEnd) {
  //       this.showSidebarAndNavbar = !this.router.url.includes('/login');
  //     }
  //   });
  // }

  // constructor(private router: Router) {}

  // ngOnInit() {
  //   this.router.events
  //     .pipe(filter((event: NavigationEvent) => event instanceof NavigationEnd))
  //     .subscribe((event: NavigationEnd) => {
  //       this.showSidebarAndNavbar = !event.url.includes('/login');
  //     });
  // }

  toggleSidebar() {
    if (this.sidebar) {
      this.sidebar.toggleSidebar();
    }
  }
}
