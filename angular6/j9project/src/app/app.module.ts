import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from '@app/app-routing.module';
import { AppComponent } from '@app/features';

import { SharedModule } from '@app/shared';
import { CoreModule } from '@app/core';

//NG Bootstrap
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    BrowserModule,

    //NgbModule
    NgbModule.forRoot(),

    // core & shared
    CoreModule,
    SharedModule,

    // app
    AppRoutingModule
  ],
  declarations: [AppComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
