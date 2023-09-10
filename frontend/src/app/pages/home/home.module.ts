import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomePageComponent} from './home-page/home-page.component';
import {RouterModule, Routes} from "@angular/router";
import {APP_CONSTANTS} from "../../app.constant";

const homeRoutes: Routes = [
    {path: APP_CONSTANTS.routerLinks.home, component: HomePageComponent}
];

const MODULES = [CommonModule, RouterModule.forChild(homeRoutes)];

const COMPONENTS = [HomePageComponent];

@NgModule({
    declarations: [COMPONENTS],
    imports: [MODULES],
    exports: [RouterModule],
})
export class HomeModule {
}
