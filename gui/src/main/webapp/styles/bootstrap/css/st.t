html 
    font-family sans-serif
    -webkit-text-size-adjust 100%
    -ms-text-size-adjust 100%
    font-size 10px
    -webkit-tap-highlight-color rgba(0, 0, 0, 0)
body 
    margin 0
    font-family "Helvetica Neue", Helvetica, Arial, sans-serif
    font-size 14px
    line-height 1.42857143
    color #333
    background-color #fff
article,
aside,
details,
figcaption,
figure,
footer,
header,
hgroup,
main,
nav,
section,
summary 
    display block
audio,
canvas,
progress,
video 
    display inline-block
    vertical-align baseline
audio 
    &:not([controls]) 
        display none
        height 0
[hidden],
template 
    display none
a 
    background transparent
    color #428bca
    text-decoration none
    &:focus 
        outline thin dotted
        outline 5px auto -webkit-focus-ring-color
        outline-offset -2px
    &.text-primary 
        &:hover 
            color #3071a9
    &.text-success 
        &:hover 
            color #2b542c
    &.text-info 
        &:hover 
            color #245269
    &.text-warning 
        &:hover 
            color #66512c
    &.text-danger 
        &:hover 
            color #843534
    &.bg-primary 
        &:hover 
            background-color #3071a9
    &.bg-success 
        &:hover 
            background-color #c1e2b3
    &.bg-info 
        &:hover 
            background-color #afd9ee
    &.bg-warning 
        &:hover 
            background-color #f7ecb5
    &.bg-danger 
        &:hover 
            background-color #e4b9b9
    &.list-group-item 
        color #555
        .list-group-item-heading 
            color #333
    &.list-group-item-success 
        color #3c763d
        .list-group-item-heading 
            color inherit
    &.list-group-item-info 
        color #31708f
        .list-group-item-heading 
            color inherit
    &.list-group-item-warning 
        color #8a6d3b
        .list-group-item-heading 
            color inherit
    &.list-group-item-danger 
        color #a94442
        .list-group-item-heading 
            color inherit
a:active,
a:hover 
    outline 0
abbr[title] 
    border-bottom 1px dotted
b,
strong 
    font-weight bold
dfn 
    font-style italic
h1 
    margin .67em 0
    font-size 2em
mark 
    color #000
    background #ff0
small 
    font-size 80%
sub,
sup 
    position relative
    font-size 75%
    line-height 0
    vertical-align baseline
sup 
    top -.5em
sub 
    bottom -.25em
img 
    border 0
    max-width 100% !important
    vertical-align middle
svg 
    &:not(:root) 
        overflow hidden
figure 
    margin 1em 40px
    margin 0
hr 
    height 0
    -webkit-box-sizing content-box
    -moz-box-sizing content-box
    box-sizing content-box
    margin-top 20px
    margin-bottom 20px
    border 0
    border-top 1px solid #eee
pre 
    overflow auto
    display block
    padding 9.5px
    margin 0 0 10px
    font-size 13px
    line-height 1.42857143
    color #333
    word-break break-all
    word-wrap break-word
    background-color #f5f5f5
    border 1px solid #ccc
    border-radius 4px
    code 
        padding 0
        font-size inherit
        color inherit
        white-space pre-wrap
        background-color transparent
        border-radius 0
code,
kbd,
pre,
samp 
    font-family monospace, monospace
    font-size 1em
    font-family Menlo, Monaco, Consolas, "Courier New", monospace
button,
input,
optgroup,
select,
textarea 
    margin 0
    font inherit
    color inherit
button 
    overflow visible
    &.close 
        -webkit-appearance none
        padding 0
        cursor pointer
        background transparent
        border 0
button,
select 
    text-transform none
button,
html input[type="button"],
input[type="reset"],
input[type="submit"] 
    -webkit-appearance button
    cursor pointer
button[disabled],
html input[disabled] 
    cursor default
button::-moz-focus-inner,
input::-moz-focus-inner 
    padding 0
    border 0
input 
    line-height normal
input[type="checkbox"],
input[type="radio"] 
    -webkit-box-sizing border-box
    -moz-box-sizing border-box
    box-sizing border-box
    padding 0
input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button 
    height auto
input[type="search"] 
    -webkit-box-sizing content-box
    -moz-box-sizing content-box
    box-sizing content-box
    -webkit-appearance textfield
    -webkit-box-sizing border-box
    -moz-box-sizing border-box
    box-sizing border-box
    -webkit-appearance none
input[type="search"]::-webkit-search-cancel-button,
input[type="search"]::-webkit-search-decoration 
    -webkit-appearance none
fieldset 
    padding .35em .625em .75em
    margin 0 2px
    border 1px solid #c0c0c0
legend 
    padding 0
    border 0
    display block
    width 100%
    padding 0
    margin-bottom 20px
    font-size 21px
    line-height inherit
    color #333
    border 0
    border-bottom 1px solid #e5e5e5
textarea 
    overflow auto
    &.form-control 
        height auto
optgroup 
    font-weight bold
table 
    border-spacing 0
    border-collapse collapse
    col[class*="col-"] 
        position static
        display table-column
        float none
    &.visible-xs 
        display table
    &.visible-sm 
        display table
    &.visible-md 
        display table
    &.visible-lg 
        display table
    &.visible-print 
        display table
td,
th 
    padding 0
@media 
    print 
        * {
    color #000 !important
        text-shadow none !important
        background transparent !important
        -webkit-box-shadow none !important
        box-shadow none !important
        .visible-print {
    display block !important
        .visible-print-block {
    display block !important
        .visible-print-inline {
    display inline !important
        .visible-print-inline-block {
    display inline-block !important
    (min-width 
        &: 
            768px) 
                .lead {
    font-size 21px
                .dl-horizontal dt {
    float left
                width 160px
                overflow hidden
                clear left
                text-align right
                text-overflow ellipsis
                white-space nowrap
                .container {
    width 750px
                .col-sm-1, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-sm-10, .col-sm-11, .col-sm-12 {
    float left
                .form-inline .form-group {
    display inline-block
                margin-bottom 0
                vertical-align middle
                .form-horizontal .control-label {
    padding-top 7px
                margin-bottom 0
                text-align right
                .form-horizontal .form-group-lg .control-label {
    padding-top 14.3px
                .navbar-right .dropdown-menu {
    right 0
                left auto
                .nav-tabs.nav-justified > li {
    display table-cell
                width 1%
                .nav-tabs.nav-justified > li > a {
    border-bottom 1px solid #ddd
                border-radius 4px 4px 0 0
                .nav-justified > li {
    display table-cell
                width 1%
                .nav-tabs-justified > li > a {
    border-bottom 1px solid #ddd
                border-radius 4px 4px 0 0
                .navbar {
    border-radius 4px
                .navbar-collapse {
    width auto
                border-top 0
                -webkit-box-shadow none
                box-shadow none
                .container > .navbar-header,
  .container-fluid > .navbar-header,
  .container > .navbar-collapse,
  .container-fluid > .navbar-collapse {
    margin-right 0
                margin-left 0
                .navbar-static-top {
    border-radius 0
                .navbar-fixed-top,
  .navbar-fixed-bottom {
    border-radius 0
                .navbar > .container .navbar-brand,
  .navbar > .container-fluid .navbar-brand {
    margin-left -15px
                .navbar-toggle {
    display none
                .navbar-form .form-group {
    display inline-block
                margin-bottom 0
                vertical-align middle
                .navbar-text {
    float left
                margin-right 15px
                margin-left 15px
                .modal-dialog {
    width 600px
                margin 30px auto
    screen 
        and 
            (max-width 
                &: 
                    767px) 
                        .table-responsive {
    width 100%
                        margin-bottom 15px
                        overflow-x auto
                        overflow-y hidden
                        -webkit-overflow-scrolling touch
                        -ms-overflow-style -ms-autohiding-scrollbar
                        border 1px solid #ddd
            (min-width 
                &: 
                    768px) 
                        .jumbotron {
    padding-top 48px
                        padding-bottom 48px
                        .carousel-control .glyphicon-chevron-left,
  .carousel-control .glyphicon-chevron-right,
  .carousel-control .icon-prev,
  .carousel-control .icon-next {
    width 30px
                        height 30px
                        margin-top -15px
                        font-size 30px
    (max-width 
        &: 
            480px) 
                and 
                    (orientation 
                        &: 
                            landscape) 
                                .navbar-fixed-top .navbar-collapse,
  .navbar-fixed-bottom .navbar-collapse {
    max-height 200px
            767px) 
                .navbar-nav .open .dropdown-menu {
    position static
                float none
                width auto
                margin-top 0
                background-color transparent
                border 0
                -webkit-box-shadow none
                box-shadow none
                .navbar-default .navbar-nav .open .dropdown-menu > li > a {
    color #777
                .navbar-inverse .navbar-nav .open .dropdown-menu > .dropdown-header {
    border-color #080808
                .visible-xs {
    display block !important
a,
  a:visited 
    text-decoration underline
a[href]:after 
    content " (" attr(href) ")"
abbr[title]:after 
    content " (" attr(title) ")"
a[href^="javascript:"]:after,
  a[href^="#"]:after 
    content ""
pre,
  blockquote 
    border 1px solid #999
    page-break-inside avoid
thead 
    display table-header-group
tr,
  img 
    page-break-inside avoid
p,
  h2,
  h3 
    orphans 3
    widows 3
h2,
  h3 
    page-break-after avoid
select 
    background #fff !important
    &.input-sm 
        height 30px
        line-height 30px
    &.input-lg 
        height 46px
        line-height 46px
.navbar 
    display none
    position relative
    min-height 50px
    margin-bottom 20px
    border 1px solid transparent
.table td,
  .table th 
    background-color #fff !important
.btn > .caret,
  .dropup > .btn > .caret 
    border-top-color #000 !important
.label 
    border 1px solid #000
    display inline
    padding .2em .6em .3em
    font-size 75%
    font-weight bold
    line-height 1
    color #fff
    text-align center
    white-space nowrap
    vertical-align baseline
    border-radius .25em
    &:empty 
        display none
.table 
    border-collapse collapse !important
    width 100%
    max-width 100%
    margin-bottom 20px
    & > thead 
        & > tr 
            & > th 
                vertical-align bottom
                border-bottom 2px solid #ddd
    & > tbody 
        & + tbody 
            border-top 2px solid #ddd
    .table 
        background-color #fff
.table-bordered th,
  .table-bordered td 
    border 1px solid #ddd !important
} 
    @font-face 
        font-family 'Glyphicons Halflings'
        src url('../fonts/glyphicons-halflings-regular.eot')
        src url('../fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), url('../fonts/glyphicons-halflings-regular.woff') format('woff'), url('../fonts/glyphicons-halflings-regular.ttf') format('truetype'), url('../fonts/glyphicons-halflings-regular.svg#glyphicons_halflingsregular') format('svg')
    @media 
        (min-width 
            &: 
                992px) 
                    .container {
    width 970px
                    .col-md-1, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-md-10, .col-md-11, .col-md-12 {
    float left
                    .modal-lg {
    width 900px
                    and 
                        (max-width 
                            &: 
                                1199px) 
                                    .visible-md {
    display block !important
                                    .visible-md-block {
    display block !important
                                    .visible-md-inline {
    display inline !important
                                    .visible-md-inline-block {
    display inline-block !important
                                    .hidden-md {
    display none !important
                1200px) 
                    .container {
    width 1170px
                    .col-lg-1, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-lg-10, .col-lg-11, .col-lg-12 {
    float left
                    .visible-lg {
    display block !important
                    .visible-lg-block {
    display block !important
                    .visible-lg-inline {
    display inline !important
                    .visible-lg-inline-block {
    display inline-block !important
                    .hidden-lg {
    display none !important
                768px) 
                    .form-horizontal .form-group-sm .control-label {
    padding-top 6px
                    .navbar-header {
    float left
                    .navbar-nav {
    float left
                    margin 0
                    .navbar-left {
    float left !important
                    .navbar-form {
    width auto
                    padding-top 0
                    padding-bottom 0
                    margin-right 0
                    margin-left 0
                    border 0
                    -webkit-box-shadow none
                    box-shadow none
                    and 
                        (max-width 
                            &: 
                                991px) 
                                    .visible-sm {
    display block !important
                                    .visible-sm-block {
    display block !important
                                    .visible-sm-inline {
    display inline !important
                                    .visible-sm-inline-block {
    display inline-block !important
                                    .hidden-sm {
    display none !important
        (max-width 
            &: 
                767px) 
                    .navbar-form .form-group {
    margin-bottom 5px
                    .visible-xs-block {
    display block !important
                    .visible-xs-inline {
    display inline !important
                    .visible-xs-inline-block {
    display inline-block !important
                    .hidden-xs {
    display none !important
        print 
            .hidden-print {
    display none !important
    .container-fluid 
        padding-right 15px
        padding-left 15px
        margin-right auto
        margin-left auto
    table 
        background-color transparent
    fieldset 
        min-width 0
        padding 0
        margin 0
        border 0
    .form-horizontal 
        .has-feedback 
            .form-control-feedback 
                top 0
                right 15px
    .btn 
        display inline-block
        padding 6px 12px
        margin-bottom 0
        font-size 14px
        font-weight normal
        line-height 1.42857143
        text-align center
        white-space nowrap
        vertical-align middle
        cursor pointer
        -webkit-user-select none
        -moz-user-select none
        -ms-user-select none
        user-select none
        background-image none
        border 1px solid transparent
        border-radius 4px
    .nav-tabs 
        &.nav-justified 
            & > li 
                & > a 
                    margin-right 0
                    border-radius 4px
    .nav-pills 
        & > li 
            float left
    .nav-tabs-justified 
        border-bottom 0
    .tab-content 
        & > .tab-pane 
            display none
    .navbar-collapse 
        padding-right 15px
        padding-left 15px
        overflow-x visible
        -webkit-overflow-scrolling touch
        border-top 1px solid transparent
        -webkit-box-shadow inset 0 1px 0 rgba(255, 255, 255, .1)
        box-shadow inset 0 1px 0 rgba(255, 255, 255, .1)
    .navbar-static-top 
        z-index 1000
        border-width 0 0 1px
    .navbar-fixed-top 
        top 0
        border-width 0 0 1px
    .navbar-toggle 
        position relative
        float right
        padding 9px 10px
        margin-top 8px
        margin-right 15px
        margin-bottom 8px
        background-color transparent
        background-image none
        border 1px solid transparent
        border-radius 4px
    .navbar-nav 
        margin 7.5px -15px
        & > li 
            & > .dropdown-menu 
                margin-top 0
                border-top-left-radius 0
                border-top-right-radius 0
    .navbar-form 
        padding 10px 15px
        margin-top 8px
        margin-right -15px
        margin-bottom 8px
        margin-left -15px
        border-top 1px solid transparent
        border-bottom 1px solid transparent
        -webkit-box-shadow inset 0 1px 0 rgba(255, 255, 255, .1), 0 1px 0 rgba(255, 255, 255, .1)
        box-shadow inset 0 1px 0 rgba(255, 255, 255, .1), 0 1px 0 rgba(255, 255, 255, .1)
    .navbar-default 
        background-color #f8f8f8
        border-color #e7e7e7
        .navbar-link 
            color #777
    .navbar-inverse 
        .navbar-link 
            color #777
    .thumbnail 
        display block
        padding 4px
        margin-bottom 20px
        line-height 1.42857143
        background-color #fff
        border 1px solid #ddd
        border-radius 4px
        -webkit-transition all .2s ease-in-out
        -o-transition all .2s ease-in-out
        transition all .2s ease-in-out
    @-o-keyframes 
        progress-bar-stripes 
            from {
    background-position 40px 0
    @keyframes 
        progress-bar-stripes 
            from {
    background-position 40px 0
    .progress 
        height 20px
        margin-bottom 20px
        overflow hidden
        background-color #f5f5f5
        border-radius 4px
        -webkit-box-shadow inset 0 1px 2px rgba(0, 0, 0, .1)
        box-shadow inset 0 1px 2px rgba(0, 0, 0, .1)
    .tooltip 
        position absolute
        z-index 1070
        display block
        font-size 12px
        line-height 1.4
        visibility visible
        filter alpha(opacity=0)
        opacity 0
    .visible-print 
        display none !important
    .visible-print-block 
        display none !important
    .visible-print-inline 
        display none !important
    .visible-print-inline-block 
        display none !important
.glyphicon 
    position relative
    top 1px
    display inline-block
    font-family 'Glyphicons Halflings'
    font-style normal
    font-weight normal
    line-height 1
    -webkit-font-smoothing antialiased
    -moz-osx-font-smoothing grayscale
.glyphicon-asterisk 
    &:before 
        content "\2a"
.glyphicon-plus 
    &:before 
        content "\2b"
.glyphicon-euro 
    &:before 
        content "\20ac"
.glyphicon-minus 
    &:before 
        content "\2212"
.glyphicon-cloud 
    &:before 
        content "\2601"
.glyphicon-envelope 
    &:before 
        content "\2709"
.glyphicon-pencil 
    &:before 
        content "\270f"
.glyphicon-glass 
    &:before 
        content "\e001"
.glyphicon-music 
    &:before 
        content "\e002"
.glyphicon-search 
    &:before 
        content "\e003"
.glyphicon-heart 
    &:before 
        content "\e005"
.glyphicon-star 
    &:before 
        content "\e006"
.glyphicon-star-empty 
    &:before 
        content "\e007"
.glyphicon-user 
    &:before 
        content "\e008"
.glyphicon-film 
    &:before 
        content "\e009"
.glyphicon-th-large 
    &:before 
        content "\e010"
.glyphicon-th 
    &:before 
        content "\e011"
.glyphicon-th-list 
    &:before 
        content "\e012"
.glyphicon-ok 
    &:before 
        content "\e013"
.glyphicon-remove 
    &:before 
        content "\e014"
.glyphicon-zoom-in 
    &:before 
        content "\e015"
.glyphicon-zoom-out 
    &:before 
        content "\e016"
.glyphicon-off 
    &:before 
        content "\e017"
.glyphicon-signal 
    &:before 
        content "\e018"
.glyphicon-cog 
    &:before 
        content "\e019"
.glyphicon-trash 
    &:before 
        content "\e020"
.glyphicon-home 
    &:before 
        content "\e021"
.glyphicon-file 
    &:before 
        content "\e022"
.glyphicon-time 
    &:before 
        content "\e023"
.glyphicon-road 
    &:before 
        content "\e024"
.glyphicon-download-alt 
    &:before 
        content "\e025"
.glyphicon-download 
    &:before 
        content "\e026"
.glyphicon-upload 
    &:before 
        content "\e027"
.glyphicon-inbox 
    &:before 
        content "\e028"
.glyphicon-play-circle 
    &:before 
        content "\e029"
.glyphicon-repeat 
    &:before 
        content "\e030"
.glyphicon-refresh 
    &:before 
        content "\e031"
.glyphicon-list-alt 
    &:before 
        content "\e032"
.glyphicon-lock 
    &:before 
        content "\e033"
.glyphicon-flag 
    &:before 
        content "\e034"
.glyphicon-headphones 
    &:before 
        content "\e035"
.glyphicon-volume-off 
    &:before 
        content "\e036"
.glyphicon-volume-down 
    &:before 
        content "\e037"
.glyphicon-volume-up 
    &:before 
        content "\e038"
.glyphicon-qrcode 
    &:before 
        content "\e039"
.glyphicon-barcode 
    &:before 
        content "\e040"
.glyphicon-tag 
    &:before 
        content "\e041"
.glyphicon-tags 
    &:before 
        content "\e042"
.glyphicon-book 
    &:before 
        content "\e043"
.glyphicon-bookmark 
    &:before 
        content "\e044"
.glyphicon-print 
    &:before 
        content "\e045"
.glyphicon-camera 
    &:before 
        content "\e046"
.glyphicon-font 
    &:before 
        content "\e047"
.glyphicon-bold 
    &:before 
        content "\e048"
.glyphicon-italic 
    &:before 
        content "\e049"
.glyphicon-text-height 
    &:before 
        content "\e050"
.glyphicon-text-width 
    &:before 
        content "\e051"
.glyphicon-align-left 
    &:before 
        content "\e052"
.glyphicon-align-center 
    &:before 
        content "\e053"
.glyphicon-align-right 
    &:before 
        content "\e054"
.glyphicon-align-justify 
    &:before 
        content "\e055"
.glyphicon-list 
    &:before 
        content "\e056"
.glyphicon-indent-left 
    &:before 
        content "\e057"
.glyphicon-indent-right 
    &:before 
        content "\e058"
.glyphicon-facetime-video 
    &:before 
        content "\e059"
.glyphicon-picture 
    &:before 
        content "\e060"
.glyphicon-map-marker 
    &:before 
        content "\e062"
.glyphicon-adjust 
    &:before 
        content "\e063"
.glyphicon-tint 
    &:before 
        content "\e064"
.glyphicon-edit 
    &:before 
        content "\e065"
.glyphicon-share 
    &:before 
        content "\e066"
.glyphicon-check 
    &:before 
        content "\e067"
.glyphicon-move 
    &:before 
        content "\e068"
.glyphicon-step-backward 
    &:before 
        content "\e069"
.glyphicon-fast-backward 
    &:before 
        content "\e070"
.glyphicon-backward 
    &:before 
        content "\e071"
.glyphicon-play 
    &:before 
        content "\e072"
.glyphicon-pause 
    &:before 
        content "\e073"
.glyphicon-stop 
    &:before 
        content "\e074"
.glyphicon-forward 
    &:before 
        content "\e075"
.glyphicon-fast-forward 
    &:before 
        content "\e076"
.glyphicon-step-forward 
    &:before 
        content "\e077"
.glyphicon-eject 
    &:before 
        content "\e078"
.glyphicon-chevron-left 
    &:before 
        content "\e079"
.glyphicon-chevron-right 
    &:before 
        content "\e080"
.glyphicon-plus-sign 
    &:before 
        content "\e081"
.glyphicon-minus-sign 
    &:before 
        content "\e082"
.glyphicon-remove-sign 
    &:before 
        content "\e083"
.glyphicon-ok-sign 
    &:before 
        content "\e084"
.glyphicon-question-sign 
    &:before 
        content "\e085"
.glyphicon-info-sign 
    &:before 
        content "\e086"
.glyphicon-screenshot 
    &:before 
        content "\e087"
.glyphicon-remove-circle 
    &:before 
        content "\e088"
.glyphicon-ok-circle 
    &:before 
        content "\e089"
.glyphicon-ban-circle 
    &:before 
        content "\e090"
.glyphicon-arrow-left 
    &:before 
        content "\e091"
.glyphicon-arrow-right 
    &:before 
        content "\e092"
.glyphicon-arrow-up 
    &:before 
        content "\e093"
.glyphicon-arrow-down 
    &:before 
        content "\e094"
.glyphicon-share-alt 
    &:before 
        content "\e095"
.glyphicon-resize-full 
    &:before 
        content "\e096"
.glyphicon-resize-small 
    &:before 
        content "\e097"
.glyphicon-exclamation-sign 
    &:before 
        content "\e101"
.glyphicon-gift 
    &:before 
        content "\e102"
.glyphicon-leaf 
    &:before 
        content "\e103"
.glyphicon-fire 
    &:before 
        content "\e104"
.glyphicon-eye-open 
    &:before 
        content "\e105"
.glyphicon-eye-close 
    &:before 
        content "\e106"
.glyphicon-warning-sign 
    &:before 
        content "\e107"
.glyphicon-plane 
    &:before 
        content "\e108"
.glyphicon-calendar 
    &:before 
        content "\e109"
.glyphicon-random 
    &:before 
        content "\e110"
.glyphicon-comment 
    &:before 
        content "\e111"
.glyphicon-magnet 
    &:before 
        content "\e112"
.glyphicon-chevron-up 
    &:before 
        content "\e113"
.glyphicon-chevron-down 
    &:before 
        content "\e114"
.glyphicon-retweet 
    &:before 
        content "\e115"
.glyphicon-shopping-cart 
    &:before 
        content "\e116"
.glyphicon-folder-close 
    &:before 
        content "\e117"
.glyphicon-folder-open 
    &:before 
        content "\e118"
.glyphicon-resize-vertical 
    &:before 
        content "\e119"
.glyphicon-resize-horizontal 
    &:before 
        content "\e120"
.glyphicon-hdd 
    &:before 
        content "\e121"
.glyphicon-bullhorn 
    &:before 
        content "\e122"
.glyphicon-bell 
    &:before 
        content "\e123"
.glyphicon-certificate 
    &:before 
        content "\e124"
.glyphicon-thumbs-up 
    &:before 
        content "\e125"
.glyphicon-thumbs-down 
    &:before 
        content "\e126"
.glyphicon-hand-right 
    &:before 
        content "\e127"
.glyphicon-hand-left 
    &:before 
        content "\e128"
.glyphicon-hand-up 
    &:before 
        content "\e129"
.glyphicon-hand-down 
    &:before 
        content "\e130"
.glyphicon-circle-arrow-right 
    &:before 
        content "\e131"
.glyphicon-circle-arrow-left 
    &:before 
        content "\e132"
.glyphicon-circle-arrow-up 
    &:before 
        content "\e133"
.glyphicon-circle-arrow-down 
    &:before 
        content "\e134"
.glyphicon-globe 
    &:before 
        content "\e135"
.glyphicon-wrench 
    &:before 
        content "\e136"
.glyphicon-tasks 
    &:before 
        content "\e137"
.glyphicon-filter 
    &:before 
        content "\e138"
.glyphicon-briefcase 
    &:before 
        content "\e139"
.glyphicon-fullscreen 
    &:before 
        content "\e140"
.glyphicon-dashboard 
    &:before 
        content "\e141"
.glyphicon-paperclip 
    &:before 
        content "\e142"
.glyphicon-heart-empty 
    &:before 
        content "\e143"
.glyphicon-link 
    &:before 
        content "\e144"
.glyphicon-phone 
    &:before 
        content "\e145"
.glyphicon-pushpin 
    &:before 
        content "\e146"
.glyphicon-usd 
    &:before 
        content "\e148"
.glyphicon-gbp 
    &:before 
        content "\e149"
.glyphicon-sort 
    &:before 
        content "\e150"
.glyphicon-sort-by-alphabet 
    &:before 
        content "\e151"
.glyphicon-sort-by-alphabet-alt 
    &:before 
        content "\e152"
.glyphicon-sort-by-order 
    &:before 
        content "\e153"
.glyphicon-sort-by-order-alt 
    &:before 
        content "\e154"
.glyphicon-sort-by-attributes 
    &:before 
        content "\e155"
.glyphicon-sort-by-attributes-alt 
    &:before 
        content "\e156"
.glyphicon-unchecked 
    &:before 
        content "\e157"
.glyphicon-expand 
    &:before 
        content "\e158"
.glyphicon-collapse-down 
    &:before 
        content "\e159"
.glyphicon-collapse-up 
    &:before 
        content "\e160"
.glyphicon-log-in 
    &:before 
        content "\e161"
.glyphicon-flash 
    &:before 
        content "\e162"
.glyphicon-log-out 
    &:before 
        content "\e163"
.glyphicon-new-window 
    &:before 
        content "\e164"
.glyphicon-record 
    &:before 
        content "\e165"
.glyphicon-save 
    &:before 
        content "\e166"
.glyphicon-open 
    &:before 
        content "\e167"
.glyphicon-saved 
    &:before 
        content "\e168"
.glyphicon-import 
    &:before 
        content "\e169"
.glyphicon-export 
    &:before 
        content "\e170"
.glyphicon-send 
    &:before 
        content "\e171"
.glyphicon-floppy-disk 
    &:before 
        content "\e172"
.glyphicon-floppy-saved 
    &:before 
        content "\e173"
.glyphicon-floppy-remove 
    &:before 
        content "\e174"
.glyphicon-floppy-save 
    &:before 
        content "\e175"
.glyphicon-floppy-open 
    &:before 
        content "\e176"
.glyphicon-credit-card 
    &:before 
        content "\e177"
.glyphicon-transfer 
    &:before 
        content "\e178"
.glyphicon-cutlery 
    &:before 
        content "\e179"
.glyphicon-header 
    &:before 
        content "\e180"
.glyphicon-compressed 
    &:before 
        content "\e181"
.glyphicon-earphone 
    &:before 
        content "\e182"
.glyphicon-phone-alt 
    &:before 
        content "\e183"
.glyphicon-tower 
    &:before 
        content "\e184"
.glyphicon-stats 
    &:before 
        content "\e185"
.glyphicon-sd-video 
    &:before 
        content "\e186"
.glyphicon-hd-video 
    &:before 
        content "\e187"
.glyphicon-subtitles 
    &:before 
        content "\e188"
.glyphicon-sound-stereo 
    &:before 
        content "\e189"
.glyphicon-sound-dolby 
    &:before 
        content "\e190"
.glyphicon-sound-5-1 
    &:before 
        content "\e191"
.glyphicon-sound-6-1 
    &:before 
        content "\e192"
.glyphicon-sound-7-1 
    &:before 
        content "\e193"
.glyphicon-copyright-mark 
    &:before 
        content "\e194"
.glyphicon-registration-mark 
    &:before 
        content "\e195"
.glyphicon-cloud-download 
    &:before 
        content "\e197"
.glyphicon-cloud-upload 
    &:before 
        content "\e198"
.glyphicon-tree-conifer 
    &:before 
        content "\e199"
.glyphicon-tree-deciduous 
    &:before 
        content "\e200"
* 
    -webkit-box-sizing border-box
    -moz-box-sizing border-box
    box-sizing border-box
*:before,
*:after 
    -webkit-box-sizing border-box
    -moz-box-sizing border-box
    box-sizing border-box
input,
button,
select,
textarea 
    font-family inherit
    font-size inherit
    line-height inherit
a:hover,
a:focus 
    color #2a6496
    text-decoration underline
.img-responsive,
.thumbnail > img,
.thumbnail a > img,
.carousel-inner > .item > img,
.carousel-inner > .item > a > img 
    display block
    width 100% \9
    max-width 100%
    height auto
.img-rounded 
    border-radius 6px
.img-thumbnail 
    display inline-block
    width 100% \9
    max-width 100%
    height auto
    padding 4px
    line-height 1.42857143
    background-color #fff
    border 1px solid #ddd
    border-radius 4px
    -webkit-transition all .2s ease-in-out
    -o-transition all .2s ease-in-out
    transition all .2s ease-in-out
.img-circle 
    border-radius 50%
.sr-only 
    position absolute
    width 1px
    height 1px
    padding 0
    margin -1px
    overflow hidden
    clip rect(0, 0, 0, 0)
    border 0
.sr-only-focusable:active,
.sr-only-focusable:focus 
    position static
    width auto
    height auto
    margin 0
    overflow visible
    clip auto
h1,
h2,
h3,
h4,
h5,
h6,
.h1,
.h2,
.h3,
.h4,
.h5,
.h6 
    font-family inherit
    font-weight 500
    line-height 1.1
    color inherit
h1 small,
h2 small,
h3 small,
h4 small,
h5 small,
h6 small,
.h1 small,
.h2 small,
.h3 small,
.h4 small,
.h5 small,
.h6 small,
h1 .small,
h2 .small,
h3 .small,
h4 .small,
h5 .small,
h6 .small,
.h1 .small,
.h2 .small,
.h3 .small,
.h4 .small,
.h5 .small,
.h6 .small 
    font-weight normal
    line-height 1
    color #777
h1,
.h1,
h2,
.h2,
h3,
.h3 
    margin-top 20px
    margin-bottom 10px
h1 small,
.h1 small,
h2 small,
.h2 small,
h3 small,
.h3 small,
h1 .small,
.h1 .small,
h2 .small,
.h2 .small,
h3 .small,
.h3 .small 
    font-size 65%
h4,
.h4,
h5,
.h5,
h6,
.h6 
    margin-top 10px
    margin-bottom 10px
h4 small,
.h4 small,
h5 small,
.h5 small,
h6 small,
.h6 small,
h4 .small,
.h4 .small,
h5 .small,
.h5 .small,
h6 .small,
.h6 .small 
    font-size 75%
h1,
.h1 
    font-size 36px
h2,
.h2 
    font-size 30px
h3,
.h3 
    font-size 24px
h4,
.h4 
    font-size 18px
h5,
.h5 
    font-size 14px
h6,
.h6 
    font-size 12px
p 
    margin 0 0 10px
.lead 
    margin-bottom 20px
    font-size 16px
    font-weight 300
    line-height 1.4
}
small,
.small 
    font-size 85%
cite 
    font-style normal
mark,
.mark 
    padding .2em
    background-color #fcf8e3
.text-left 
    text-align left
.text-right 
    text-align right
.text-center 
    text-align center
.text-justify 
    text-align justify
.text-nowrap 
    white-space nowrap
.text-lowercase 
    text-transform lowercase
.text-uppercase 
    text-transform uppercase
.text-capitalize 
    text-transform capitalize
.text-muted 
    color #777
.text-primary 
    color #428bca
.text-success 
    color #3c763d
.text-info 
    color #31708f
.text-warning 
    color #8a6d3b
.text-danger 
    color #a94442
.bg-primary 
    color #fff
    background-color #428bca
.bg-success 
    background-color #dff0d8
.bg-info 
    background-color #d9edf7
.bg-warning 
    background-color #fcf8e3
.bg-danger 
    background-color #f2dede
.page-header 
    padding-bottom 9px
    margin 40px 0 20px
    border-bottom 1px solid #eee
ul,
ol 
    margin-top 0
    margin-bottom 10px
ul ul,
ol ul,
ul ol,
ol ol 
    margin-bottom 0
.list-unstyled 
    padding-left 0
    list-style none
.list-inline 
    padding-left 0
    margin-left -5px
    list-style none
    & > li 
        display inline-block
        padding-right 5px
        padding-left 5px
dl 
    margin-top 0
    margin-bottom 20px
dt,
dd 
    line-height 1.42857143
dt 
    font-weight bold
dd 
    margin-left 0
.dl-horizontal 
    dd 
        margin-left 180px
}
abbr[title],
abbr[data-original-title] 
    cursor help
    border-bottom 1px dotted #777
.initialism 
    font-size 90%
    text-transform uppercase
blockquote 
    padding 10px 20px
    margin 0 0 20px
    font-size 17.5px
    border-left 5px solid #eee
blockquote p:last-child,
blockquote ul:last-child,
blockquote ol:last-child 
    margin-bottom 0
blockquote footer,
blockquote small,
blockquote .small 
    display block
    font-size 80%
    line-height 1.42857143
    color #777
blockquote footer:before,
blockquote small:before,
blockquote .small:before 
    content '\2014 \00A0'
.blockquote-reverse,
blockquote.pull-right 
    padding-right 15px
    padding-left 0
    text-align right
    border-right 5px solid #eee
    border-left 0
.blockquote-reverse footer:before,
blockquote.pull-right footer:before,
.blockquote-reverse small:before,
blockquote.pull-right small:before,
.blockquote-reverse .small:before,
blockquote.pull-right .small:before 
    content ''
.blockquote-reverse footer:after,
blockquote.pull-right footer:after,
.blockquote-reverse small:after,
blockquote.pull-right small:after,
.blockquote-reverse .small:after,
blockquote.pull-right .small:after 
    content '\00A0 \2014'
blockquote:before,
blockquote:after 
    content ""
address 
    margin-bottom 20px
    font-style normal
    line-height 1.42857143
code 
    padding 2px 4px
    font-size 90%
    color #c7254e
    background-color #f9f2f4
    border-radius 4px
kbd 
    padding 2px 4px
    font-size 90%
    color #fff
    background-color #333
    border-radius 3px
    -webkit-box-shadow inset 0 -1px 0 rgba(0, 0, 0, .25)
    box-shadow inset 0 -1px 0 rgba(0, 0, 0, .25)
    kbd 
        padding 0
        font-size 100%
        -webkit-box-shadow none
        box-shadow none
.pre-scrollable 
    max-height 340px
    overflow-y scroll
.container 
    padding-right 15px
    padding-left 15px
    margin-right auto
    margin-left auto
    .jumbotron 
        border-radius 6px
        padding-right 60px
        padding-left 60px
.row 
    margin-right -15px
    margin-left -15px
.col-xs-1, .col-sm-1, .col-md-1, .col-lg-1, .col-xs-2, .col-sm-2, .col-md-2, .col-lg-2, .col-xs-3, .col-sm-3, .col-md-3, .col-lg-3, .col-xs-4, .col-sm-4, .col-md-4, .col-lg-4, .col-xs-5, .col-sm-5, .col-md-5, .col-lg-5, .col-xs-6, .col-sm-6, .col-md-6, .col-lg-6, .col-xs-7, .col-sm-7, .col-md-7, .col-lg-7, .col-xs-8, .col-sm-8, .col-md-8, .col-lg-8, .col-xs-9, .col-sm-9, .col-md-9, .col-lg-9, .col-xs-10, .col-sm-10, .col-md-10, .col-lg-10, .col-xs-11, .col-sm-11, .col-md-11, .col-lg-11, .col-xs-12, .col-sm-12, .col-md-12, .col-lg-12 
    position relative
    min-height 1px
    padding-right 15px
    padding-left 15px
.col-xs-1, .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9, .col-xs-10, .col-xs-11, .col-xs-12 
    float left
.col-xs-12 
    width 100%
.col-xs-11 
    width 91.66666667%
.col-xs-10 
    width 83.33333333%
.col-xs-9 
    width 75%
.col-xs-8 
    width 66.66666667%
.col-xs-7 
    width 58.33333333%
.col-xs-6 
    width 50%
.col-xs-5 
    width 41.66666667%
.col-xs-4 
    width 33.33333333%
.col-xs-3 
    width 25%
.col-xs-2 
    width 16.66666667%
.col-xs-1 
    width 8.33333333%
.col-xs-pull-12 
    right 100%
.col-xs-pull-11 
    right 91.66666667%
.col-xs-pull-10 
    right 83.33333333%
.col-xs-pull-9 
    right 75%
.col-xs-pull-8 
    right 66.66666667%
.col-xs-pull-7 
    right 58.33333333%
.col-xs-pull-6 
    right 50%
.col-xs-pull-5 
    right 41.66666667%
.col-xs-pull-4 
    right 33.33333333%
.col-xs-pull-3 
    right 25%
.col-xs-pull-2 
    right 16.66666667%
.col-xs-pull-1 
    right 8.33333333%
.col-xs-pull-0 
    right auto
.col-xs-push-12 
    left 100%
.col-xs-push-11 
    left 91.66666667%
.col-xs-push-10 
    left 83.33333333%
.col-xs-push-9 
    left 75%
.col-xs-push-8 
    left 66.66666667%
.col-xs-push-7 
    left 58.33333333%
.col-xs-push-6 
    left 50%
.col-xs-push-5 
    left 41.66666667%
.col-xs-push-4 
    left 33.33333333%
.col-xs-push-3 
    left 25%
.col-xs-push-2 
    left 16.66666667%
.col-xs-push-1 
    left 8.33333333%
.col-xs-push-0 
    left auto
.col-xs-offset-12 
    margin-left 100%
.col-xs-offset-11 
    margin-left 91.66666667%
.col-xs-offset-10 
    margin-left 83.33333333%
.col-xs-offset-9 
    margin-left 75%
.col-xs-offset-8 
    margin-left 66.66666667%
.col-xs-offset-7 
    margin-left 58.33333333%
.col-xs-offset-6 
    margin-left 50%
.col-xs-offset-5 
    margin-left 41.66666667%
.col-xs-offset-4 
    margin-left 33.33333333%
.col-xs-offset-3 
    margin-left 25%
.col-xs-offset-2 
    margin-left 16.66666667%
.col-xs-offset-1 
    margin-left 8.33333333%
.col-xs-offset-0 
    margin-left 0
.col-sm-12 
    width 100%
.col-sm-11 
    width 91.66666667%
.col-sm-10 
    width 83.33333333%
.col-sm-9 
    width 75%
.col-sm-8 
    width 66.66666667%
.col-sm-7 
    width 58.33333333%
.col-sm-6 
    width 50%
.col-sm-5 
    width 41.66666667%
.col-sm-4 
    width 33.33333333%
.col-sm-3 
    width 25%
.col-sm-2 
    width 16.66666667%
.col-sm-1 
    width 8.33333333%
.col-sm-pull-12 
    right 100%
.col-sm-pull-11 
    right 91.66666667%
.col-sm-pull-10 
    right 83.33333333%
.col-sm-pull-9 
    right 75%
.col-sm-pull-8 
    right 66.66666667%
.col-sm-pull-7 
    right 58.33333333%
.col-sm-pull-6 
    right 50%
.col-sm-pull-5 
    right 41.66666667%
.col-sm-pull-4 
    right 33.33333333%
.col-sm-pull-3 
    right 25%
.col-sm-pull-2 
    right 16.66666667%
.col-sm-pull-1 
    right 8.33333333%
.col-sm-pull-0 
    right auto
.col-sm-push-12 
    left 100%
.col-sm-push-11 
    left 91.66666667%
.col-sm-push-10 
    left 83.33333333%
.col-sm-push-9 
    left 75%
.col-sm-push-8 
    left 66.66666667%
.col-sm-push-7 
    left 58.33333333%
.col-sm-push-6 
    left 50%
.col-sm-push-5 
    left 41.66666667%
.col-sm-push-4 
    left 33.33333333%
.col-sm-push-3 
    left 25%
.col-sm-push-2 
    left 16.66666667%
.col-sm-push-1 
    left 8.33333333%
.col-sm-push-0 
    left auto
.col-sm-offset-12 
    margin-left 100%
.col-sm-offset-11 
    margin-left 91.66666667%
.col-sm-offset-10 
    margin-left 83.33333333%
.col-sm-offset-9 
    margin-left 75%
.col-sm-offset-8 
    margin-left 66.66666667%
.col-sm-offset-7 
    margin-left 58.33333333%
.col-sm-offset-6 
    margin-left 50%
.col-sm-offset-5 
    margin-left 41.66666667%
.col-sm-offset-4 
    margin-left 33.33333333%
.col-sm-offset-3 
    margin-left 25%
.col-sm-offset-2 
    margin-left 16.66666667%
.col-sm-offset-1 
    margin-left 8.33333333%
.col-sm-offset-0 
    margin-left 0
.col-md-12 
    width 100%
.col-md-11 
    width 91.66666667%
.col-md-10 
    width 83.33333333%
.col-md-9 
    width 75%
.col-md-8 
    width 66.66666667%
.col-md-7 
    width 58.33333333%
.col-md-6 
    width 50%
.col-md-5 
    width 41.66666667%
.col-md-4 
    width 33.33333333%
.col-md-3 
    width 25%
.col-md-2 
    width 16.66666667%
.col-md-1 
    width 8.33333333%
.col-md-pull-12 
    right 100%
.col-md-pull-11 
    right 91.66666667%
.col-md-pull-10 
    right 83.33333333%
.col-md-pull-9 
    right 75%
.col-md-pull-8 
    right 66.66666667%
.col-md-pull-7 
    right 58.33333333%
.col-md-pull-6 
    right 50%
.col-md-pull-5 
    right 41.66666667%
.col-md-pull-4 
    right 33.33333333%
.col-md-pull-3 
    right 25%
.col-md-pull-2 
    right 16.66666667%
.col-md-pull-1 
    right 8.33333333%
.col-md-pull-0 
    right auto
.col-md-push-12 
    left 100%
.col-md-push-11 
    left 91.66666667%
.col-md-push-10 
    left 83.33333333%
.col-md-push-9 
    left 75%
.col-md-push-8 
    left 66.66666667%
.col-md-push-7 
    left 58.33333333%
.col-md-push-6 
    left 50%
.col-md-push-5 
    left 41.66666667%
.col-md-push-4 
    left 33.33333333%
.col-md-push-3 
    left 25%
.col-md-push-2 
    left 16.66666667%
.col-md-push-1 
    left 8.33333333%
.col-md-push-0 
    left auto
.col-md-offset-12 
    margin-left 100%
.col-md-offset-11 
    margin-left 91.66666667%
.col-md-offset-10 
    margin-left 83.33333333%
.col-md-offset-9 
    margin-left 75%
.col-md-offset-8 
    margin-left 66.66666667%
.col-md-offset-7 
    margin-left 58.33333333%
.col-md-offset-6 
    margin-left 50%
.col-md-offset-5 
    margin-left 41.66666667%
.col-md-offset-4 
    margin-left 33.33333333%
.col-md-offset-3 
    margin-left 25%
.col-md-offset-2 
    margin-left 16.66666667%
.col-md-offset-1 
    margin-left 8.33333333%
.col-md-offset-0 
    margin-left 0
.col-lg-12 
    width 100%
.col-lg-11 
    width 91.66666667%
.col-lg-10 
    width 83.33333333%
.col-lg-9 
    width 75%
.col-lg-8 
    width 66.66666667%
.col-lg-7 
    width 58.33333333%
.col-lg-6 
    width 50%
.col-lg-5 
    width 41.66666667%
.col-lg-4 
    width 33.33333333%
.col-lg-3 
    width 25%
.col-lg-2 
    width 16.66666667%
.col-lg-1 
    width 8.33333333%
.col-lg-pull-12 
    right 100%
.col-lg-pull-11 
    right 91.66666667%
.col-lg-pull-10 
    right 83.33333333%
.col-lg-pull-9 
    right 75%
.col-lg-pull-8 
    right 66.66666667%
.col-lg-pull-7 
    right 58.33333333%
.col-lg-pull-6 
    right 50%
.col-lg-pull-5 
    right 41.66666667%
.col-lg-pull-4 
    right 33.33333333%
.col-lg-pull-3 
    right 25%
.col-lg-pull-2 
    right 16.66666667%
.col-lg-pull-1 
    right 8.33333333%
.col-lg-pull-0 
    right auto
.col-lg-push-12 
    left 100%
.col-lg-push-11 
    left 91.66666667%
.col-lg-push-10 
    left 83.33333333%
.col-lg-push-9 
    left 75%
.col-lg-push-8 
    left 66.66666667%
.col-lg-push-7 
    left 58.33333333%
.col-lg-push-6 
    left 50%
.col-lg-push-5 
    left 41.66666667%
.col-lg-push-4 
    left 33.33333333%
.col-lg-push-3 
    left 25%
.col-lg-push-2 
    left 16.66666667%
.col-lg-push-1 
    left 8.33333333%
.col-lg-push-0 
    left auto
.col-lg-offset-12 
    margin-left 100%
.col-lg-offset-11 
    margin-left 91.66666667%
.col-lg-offset-10 
    margin-left 83.33333333%
.col-lg-offset-9 
    margin-left 75%
.col-lg-offset-8 
    margin-left 66.66666667%
.col-lg-offset-7 
    margin-left 58.33333333%
.col-lg-offset-6 
    margin-left 50%
.col-lg-offset-5 
    margin-left 41.66666667%
.col-lg-offset-4 
    margin-left 33.33333333%
.col-lg-offset-3 
    margin-left 25%
.col-lg-offset-2 
    margin-left 16.66666667%
.col-lg-offset-1 
    margin-left 8.33333333%
.col-lg-offset-0 
    margin-left 0
th 
    text-align left
.table > thead > tr > th,
.table > tbody > tr > th,
.table > tfoot > tr > th,
.table > thead > tr > td,
.table > tbody > tr > td,
.table > tfoot > tr > td 
    padding 8px
    line-height 1.42857143
    vertical-align top
    border-top 1px solid #ddd
.table > caption + thead > tr:first-child > th,
.table > colgroup + thead > tr:first-child > th,
.table > thead:first-child > tr:first-child > th,
.table > caption + thead > tr:first-child > td,
.table > colgroup + thead > tr:first-child > td,
.table > thead:first-child > tr:first-child > td 
    border-top 0
.table-condensed > thead > tr > th,
.table-condensed > tbody > tr > th,
.table-condensed > tfoot > tr > th,
.table-condensed > thead > tr > td,
.table-condensed > tbody > tr > td,
.table-condensed > tfoot > tr > td 
    padding 5px
.table-bordered 
    border 1px solid #ddd
.table-bordered > thead > tr > th,
.table-bordered > tbody > tr > th,
.table-bordered > tfoot > tr > th,
.table-bordered > thead > tr > td,
.table-bordered > tbody > tr > td,
.table-bordered > tfoot > tr > td 
    border 1px solid #ddd
.table-bordered > thead > tr > th,
.table-bordered > thead > tr > td 
    border-bottom-width 2px
.table-striped > tbody > tr:nth-child(odd) > td,
.table-striped > tbody > tr:nth-child(odd) > th 
    background-color #f9f9f9
.table-hover > tbody > tr:hover > td,
.table-hover > tbody > tr:hover > th 
    background-color #f5f5f5
table td[class*="col-"],
table th[class*="col-"] 
    position static
    display table-cell
    float none
.table > thead > tr > td.active,
.table > tbody > tr > td.active,
.table > tfoot > tr > td.active,
.table > thead > tr > th.active,
.table > tbody > tr > th.active,
.table > tfoot > tr > th.active,
.table > thead > tr.active > td,
.table > tbody > tr.active > td,
.table > tfoot > tr.active > td,
.table > thead > tr.active > th,
.table > tbody > tr.active > th,
.table > tfoot > tr.active > th 
    background-color #f5f5f5
.table-hover > tbody > tr > td.active:hover,
.table-hover > tbody > tr > th.active:hover,
.table-hover > tbody > tr.active:hover > td,
.table-hover > tbody > tr:hover > .active,
.table-hover > tbody > tr.active:hover > th 
    background-color #e8e8e8
.table > thead > tr > td.success,
.table > tbody > tr > td.success,
.table > tfoot > tr > td.success,
.table > thead > tr > th.success,
.table > tbody > tr > th.success,
.table > tfoot > tr > th.success,
.table > thead > tr.success > td,
.table > tbody > tr.success > td,
.table > tfoot > tr.success > td,
.table > thead > tr.success > th,
.table > tbody > tr.success > th,
.table > tfoot > tr.success > th 
    background-color #dff0d8
.table-hover > tbody > tr > td.success:hover,
.table-hover > tbody > tr > th.success:hover,
.table-hover > tbody > tr.success:hover > td,
.table-hover > tbody > tr:hover > .success,
.table-hover > tbody > tr.success:hover > th 
    background-color #d0e9c6
.table > thead > tr > td.info,
.table > tbody > tr > td.info,
.table > tfoot > tr > td.info,
.table > thead > tr > th.info,
.table > tbody > tr > th.info,
.table > tfoot > tr > th.info,
.table > thead > tr.info > td,
.table > tbody > tr.info > td,
.table > tfoot > tr.info > td,
.table > thead > tr.info > th,
.table > tbody > tr.info > th,
.table > tfoot > tr.info > th 
    background-color #d9edf7
.table-hover > tbody > tr > td.info:hover,
.table-hover > tbody > tr > th.info:hover,
.table-hover > tbody > tr.info:hover > td,
.table-hover > tbody > tr:hover > .info,
.table-hover > tbody > tr.info:hover > th 
    background-color #c4e3f3
.table > thead > tr > td.warning,
.table > tbody > tr > td.warning,
.table > tfoot > tr > td.warning,
.table > thead > tr > th.warning,
.table > tbody > tr > th.warning,
.table > tfoot > tr > th.warning,
.table > thead > tr.warning > td,
.table > tbody > tr.warning > td,
.table > tfoot > tr.warning > td,
.table > thead > tr.warning > th,
.table > tbody > tr.warning > th,
.table > tfoot > tr.warning > th 
    background-color #fcf8e3
.table-hover > tbody > tr > td.warning:hover,
.table-hover > tbody > tr > th.warning:hover,
.table-hover > tbody > tr.warning:hover > td,
.table-hover > tbody > tr:hover > .warning,
.table-hover > tbody > tr.warning:hover > th 
    background-color #faf2cc
.table > thead > tr > td.danger,
.table > tbody > tr > td.danger,
.table > tfoot > tr > td.danger,
.table > thead > tr > th.danger,
.table > tbody > tr > th.danger,
.table > tfoot > tr > th.danger,
.table > thead > tr.danger > td,
.table > tbody > tr.danger > td,
.table > tfoot > tr.danger > td,
.table > thead > tr.danger > th,
.table > tbody > tr.danger > th,
.table > tfoot > tr.danger > th 
    background-color #f2dede
.table-hover > tbody > tr > td.danger:hover,
.table-hover > tbody > tr > th.danger:hover,
.table-hover > tbody > tr.danger:hover > td,
.table-hover > tbody > tr:hover > .danger,
.table-hover > tbody > tr.danger:hover > th 
    background-color #ebcccc
.table-responsive 
    & > .table 
        margin-bottom 0
    & > .table-bordered 
        border 0
.table-responsive > .table > thead > tr > th,
  .table-responsive > .table > tbody > tr > th,
  .table-responsive > .table > tfoot > tr > th,
  .table-responsive > .table > thead > tr > td,
  .table-responsive > .table > tbody > tr > td,
  .table-responsive > .table > tfoot > tr > td 
    white-space nowrap
.table-responsive > .table-bordered > thead > tr > th:first-child,
  .table-responsive > .table-bordered > tbody > tr > th:first-child,
  .table-responsive > .table-bordered > tfoot > tr > th:first-child,
  .table-responsive > .table-bordered > thead > tr > td:first-child,
  .table-responsive > .table-bordered > tbody > tr > td:first-child,
  .table-responsive > .table-bordered > tfoot > tr > td:first-child 
    border-left 0
.table-responsive > .table-bordered > thead > tr > th:last-child,
  .table-responsive > .table-bordered > tbody > tr > th:last-child,
  .table-responsive > .table-bordered > tfoot > tr > th:last-child,
  .table-responsive > .table-bordered > thead > tr > td:last-child,
  .table-responsive > .table-bordered > tbody > tr > td:last-child,
  .table-responsive > .table-bordered > tfoot > tr > td:last-child 
    border-right 0
.table-responsive > .table-bordered > tbody > tr:last-child > th,
  .table-responsive > .table-bordered > tfoot > tr:last-child > th,
  .table-responsive > .table-bordered > tbody > tr:last-child > td,
  .table-responsive > .table-bordered > tfoot > tr:last-child > td 
    border-bottom 0
label 
    display inline-block
    max-width 100%
    margin-bottom 5px
    font-weight bold
input[type="radio"],
input[type="checkbox"] 
    margin 4px 0 0
    margin-top 1px \9
    line-height normal
input[type="file"] 
    display block
input[type="range"] 
    display block
    width 100%
select[multiple],
select[size] 
    height auto
input[type="file"]:focus,
input[type="radio"]:focus,
input[type="checkbox"]:focus 
    outline thin dotted
    outline 5px auto -webkit-focus-ring-color
    outline-offset -2px
output 
    display block
    padding-top 7px
    font-size 14px
    line-height 1.42857143
    color #555
.form-control 
    display block
    width 100%
    height 34px
    padding 6px 12px
    font-size 14px
    line-height 1.42857143
    color #555
    background-color #fff
    background-image none
    border 1px solid #ccc
    border-radius 4px
    -webkit-box-shadow inset 0 1px 1px rgba(0, 0, 0, .075)
    box-shadow inset 0 1px 1px rgba(0, 0, 0, .075)
    -webkit-transition border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s
    -o-transition border-color ease-in-out .15s, box-shadow ease-in-out .15s
    transition border-color ease-in-out .15s, box-shadow ease-in-out .15s
    &:focus 
        border-color #66afe9
        outline 0
        -webkit-box-shadow inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6)
        box-shadow inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6)
    &::-moz-placeholder 
        color #777
        opacity 1
    &:-ms-input-placeholder 
        color #777
    &::-webkit-input-placeholder 
        color #777
.form-control[disabled],
.form-control[readonly],
fieldset[disabled] .form-control 
    cursor not-allowed
    background-color #eee
    opacity 1
input[type="date"],
input[type="time"],
input[type="datetime-local"],
input[type="month"] 
    line-height 34px
    line-height 1.42857143 \0
input[type="date"].input-sm,
input[type="time"].input-sm,
input[type="datetime-local"].input-sm,
input[type="month"].input-sm 
    line-height 30px
input[type="date"].input-lg,
input[type="time"].input-lg,
input[type="datetime-local"].input-lg,
input[type="month"].input-lg 
    line-height 46px
.form-group 
    margin-bottom 15px
.radio,
.checkbox 
    position relative
    display block
    min-height 20px
    margin-top 10px
    margin-bottom 10px
.radio label,
.checkbox label 
    padding-left 20px
    margin-bottom 0
    font-weight normal
    cursor pointer
.radio input[type="radio"],
.radio-inline input[type="radio"],
.checkbox input[type="checkbox"],
.checkbox-inline input[type="checkbox"] 
    position absolute
    margin-top 4px \9
    margin-left -20px
.radio + .radio,
.checkbox + .checkbox 
    margin-top -5px
.radio-inline,
.checkbox-inline 
    display inline-block
    padding-left 20px
    margin-bottom 0
    font-weight normal
    vertical-align middle
    cursor pointer
.radio-inline + .radio-inline,
.checkbox-inline + .checkbox-inline 
    margin-top 0
    margin-left 10px
input[type="radio"][disabled],
input[type="checkbox"][disabled],
input[type="radio"].disabled,
input[type="checkbox"].disabled,
fieldset[disabled] input[type="radio"],
fieldset[disabled] input[type="checkbox"] 
    cursor not-allowed
.radio-inline.disabled,
.checkbox-inline.disabled,
fieldset[disabled] .radio-inline,
fieldset[disabled] .checkbox-inline 
    cursor not-allowed
.radio.disabled label,
.checkbox.disabled label,
fieldset[disabled] .radio label,
fieldset[disabled] .checkbox label 
    cursor not-allowed
.form-control-static 
    padding-top 7px
    padding-bottom 7px
    margin-bottom 0
.form-control-static.input-lg,
.form-control-static.input-sm 
    padding-right 0
    padding-left 0
.input-sm,
.form-horizontal .form-group-sm .form-control 
    height 30px
    padding 5px 10px
    font-size 12px
    line-height 1.5
    border-radius 3px
textarea.input-sm,
select[multiple].input-sm 
    height auto
.input-lg,
.form-horizontal .form-group-lg .form-control 
    height 46px
    padding 10px 16px
    font-size 18px
    line-height 1.33
    border-radius 6px
textarea.input-lg,
select[multiple].input-lg 
    height auto
.has-feedback 
    position relative
    .form-control 
        padding-right 42.5px
    label 
        &.sr-only 
            & ~ .form-control-feedback 
                top 0
.form-control-feedback 
    position absolute
    top 25px
    right 0
    z-index 2
    display block
    width 34px
    height 34px
    line-height 34px
    text-align center
.input-lg 
    & + .form-control-feedback 
        width 46px
        height 46px
        line-height 46px
.input-sm 
    & + .form-control-feedback 
        width 30px
        height 30px
        line-height 30px
.has-success .help-block,
.has-success .control-label,
.has-success .radio,
.has-success .checkbox,
.has-success .radio-inline,
.has-success .checkbox-inline 
    color #3c763d
.has-success 
    .form-control 
        border-color #3c763d
        -webkit-box-shadow inset 0 1px 1px rgba(0, 0, 0, .075)
        box-shadow inset 0 1px 1px rgba(0, 0, 0, .075)
        &:focus 
            border-color #2b542c
            -webkit-box-shadow inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #67b168
            box-shadow inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #67b168
    .input-group-addon 
        color #3c763d
        background-color #dff0d8
        border-color #3c763d
    .form-control-feedback 
        color #3c763d
.has-warning .help-block,
.has-warning .control-label,
.has-warning .radio,
.has-warning .checkbox,
.has-warning .radio-inline,
.has-warning .checkbox-inline 
    color #8a6d3b
.has-warning 
    .form-control 
        border-color #8a6d3b
        -webkit-box-shadow inset 0 1px 1px rgba(0, 0, 0, .075)
        box-shadow inset 0 1px 1px rgba(0, 0, 0, .075)
        &:focus 
            border-color #66512c
            -webkit-box-shadow inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #c0a16b
            box-shadow inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #c0a16b
    .input-group-addon 
        color #8a6d3b
        background-color #fcf8e3
        border-color #8a6d3b
    .form-control-feedback 
        color #8a6d3b
.has-error .help-block,
.has-error .control-label,
.has-error .radio,
.has-error .checkbox,
.has-error .radio-inline,
.has-error .checkbox-inline 
    color #a94442
.has-error 
    .form-control 
        border-color #a94442
        -webkit-box-shadow inset 0 1px 1px rgba(0, 0, 0, .075)
        box-shadow inset 0 1px 1px rgba(0, 0, 0, .075)
        &:focus 
            border-color #843534
            -webkit-box-shadow inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #ce8483
            box-shadow inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #ce8483
    .input-group-addon 
        color #a94442
        background-color #f2dede
        border-color #a94442
    .form-control-feedback 
        color #a94442
.help-block 
    display block
    margin-top 5px
    margin-bottom 10px
    color #737373
.form-inline 
    .form-control 
        display inline-block
        width auto
        vertical-align middle
    .input-group 
        display inline-table
        vertical-align middle
        & > .form-control 
            width 100%
    .control-label 
        margin-bottom 0
        vertical-align middle
    .has-feedback 
        .form-control-feedback 
            top 0
.form-inline .input-group .input-group-addon,
  .form-inline .input-group .input-group-btn,
  .form-inline .input-group .form-control 
    width auto
.form-inline .radio,
  .form-inline .checkbox 
    display inline-block
    margin-top 0
    margin-bottom 0
    vertical-align middle
.form-inline .radio label,
  .form-inline .checkbox label 
    padding-left 0
.form-inline .radio input[type="radio"],
  .form-inline .checkbox input[type="checkbox"] 
    position relative
    margin-left 0
}
.form-horizontal .radio,
.form-horizontal .checkbox,
.form-horizontal .radio-inline,
.form-horizontal .checkbox-inline 
    padding-top 7px
    margin-top 0
    margin-bottom 0
.form-horizontal .radio,
.form-horizontal .checkbox 
    min-height 27px
.form-horizontal 
    .form-group 
        margin-right -15px
        margin-left -15px
.btn:focus,
.btn:active:focus,
.btn.active:focus 
    outline thin dotted
    outline 5px auto -webkit-focus-ring-color
    outline-offset -2px
.btn:hover,
.btn:focus 
    color #333
    text-decoration none
.btn:active,
.btn.active 
    background-image none
    outline 0
    -webkit-box-shadow inset 0 3px 5px rgba(0, 0, 0, .125)
    box-shadow inset 0 3px 5px rgba(0, 0, 0, .125)
.btn.disabled,
.btn[disabled],
fieldset[disabled] .btn 
    pointer-events none
    cursor not-allowed
    filter alpha(opacity=65)
    -webkit-box-shadow none
    box-shadow none
    opacity .65
.btn-default 
    color #333
    background-color #fff
    border-color #ccc
    .badge 
        color #fff
        background-color #333
.btn-default:hover,
.btn-default:focus,
.btn-default:active,
.btn-default.active,
.open > .dropdown-toggle.btn-default 
    color #333
    background-color #e6e6e6
    border-color #adadad
.btn-default:active,
.btn-default.active,
.open > .dropdown-toggle.btn-default 
    background-image none
.btn-default.disabled,
.btn-default[disabled],
fieldset[disabled] .btn-default,
.btn-default.disabled:hover,
.btn-default[disabled]:hover,
fieldset[disabled] .btn-default:hover,
.btn-default.disabled:focus,
.btn-default[disabled]:focus,
fieldset[disabled] .btn-default:focus,
.btn-default.disabled:active,
.btn-default[disabled]:active,
fieldset[disabled] .btn-default:active,
.btn-default.disabled.active,
.btn-default[disabled].active,
fieldset[disabled] .btn-default.active 
    background-color #fff
    border-color #ccc
.btn-primary 
    color #fff
    background-color #428bca
    border-color #357ebd
    .badge 
        color #428bca
        background-color #fff
.btn-primary:hover,
.btn-primary:focus,
.btn-primary:active,
.btn-primary.active,
.open > .dropdown-toggle.btn-primary 
    color #fff
    background-color #3071a9
    border-color #285e8e
.btn-primary:active,
.btn-primary.active,
.open > .dropdown-toggle.btn-primary 
    background-image none
.btn-primary.disabled,
.btn-primary[disabled],
fieldset[disabled] .btn-primary,
.btn-primary.disabled:hover,
.btn-primary[disabled]:hover,
fieldset[disabled] .btn-primary:hover,
.btn-primary.disabled:focus,
.btn-primary[disabled]:focus,
fieldset[disabled] .btn-primary:focus,
.btn-primary.disabled:active,
.btn-primary[disabled]:active,
fieldset[disabled] .btn-primary:active,
.btn-primary.disabled.active,
.btn-primary[disabled].active,
fieldset[disabled] .btn-primary.active 
    background-color #428bca
    border-color #357ebd
.btn-success 
    color #fff
    background-color #5cb85c
    border-color #4cae4c
    .badge 
        color #5cb85c
        background-color #fff
.btn-success:hover,
.btn-success:focus,
.btn-success:active,
.btn-success.active,
.open > .dropdown-toggle.btn-success 
    color #fff
    background-color #449d44
    border-color #398439
.btn-success:active,
.btn-success.active,
.open > .dropdown-toggle.btn-success 
    background-image none
.btn-success.disabled,
.btn-success[disabled],
fieldset[disabled] .btn-success,
.btn-success.disabled:hover,
.btn-success[disabled]:hover,
fieldset[disabled] .btn-success:hover,
.btn-success.disabled:focus,
.btn-success[disabled]:focus,
fieldset[disabled] .btn-success:focus,
.btn-success.disabled:active,
.btn-success[disabled]:active,
fieldset[disabled] .btn-success:active,
.btn-success.disabled.active,
.btn-success[disabled].active,
fieldset[disabled] .btn-success.active 
    background-color #5cb85c
    border-color #4cae4c
.btn-info 
    color #fff
    background-color #5bc0de
    border-color #46b8da
    .badge 
        color #5bc0de
        background-color #fff
.btn-info:hover,
.btn-info:focus,
.btn-info:active,
.btn-info.active,
.open > .dropdown-toggle.btn-info 
    color #fff
    background-color #31b0d5
    border-color #269abc
.btn-info:active,
.btn-info.active,
.open > .dropdown-toggle.btn-info 
    background-image none
.btn-info.disabled,
.btn-info[disabled],
fieldset[disabled] .btn-info,
.btn-info.disabled:hover,
.btn-info[disabled]:hover,
fieldset[disabled] .btn-info:hover,
.btn-info.disabled:focus,
.btn-info[disabled]:focus,
fieldset[disabled] .btn-info:focus,
.btn-info.disabled:active,
.btn-info[disabled]:active,
fieldset[disabled] .btn-info:active,
.btn-info.disabled.active,
.btn-info[disabled].active,
fieldset[disabled] .btn-info.active 
    background-color #5bc0de
    border-color #46b8da
.btn-warning 
    color #fff
    background-color #f0ad4e
    border-color #eea236
    .badge 
        color #f0ad4e
        background-color #fff
.btn-warning:hover,
.btn-warning:focus,
.btn-warning:active,
.btn-warning.active,
.open > .dropdown-toggle.btn-warning 
    color #fff
    background-color #ec971f
    border-color #d58512
.btn-warning:active,
.btn-warning.active,
.open > .dropdown-toggle.btn-warning 
    background-image none
.btn-warning.disabled,
.btn-warning[disabled],
fieldset[disabled] .btn-warning,
.btn-warning.disabled:hover,
.btn-warning[disabled]:hover,
fieldset[disabled] .btn-warning:hover,
.btn-warning.disabled:focus,
.btn-warning[disabled]:focus,
fieldset[disabled] .btn-warning:focus,
.btn-warning.disabled:active,
.btn-warning[disabled]:active,
fieldset[disabled] .btn-warning:active,
.btn-warning.disabled.active,
.btn-warning[disabled].active,
fieldset[disabled] .btn-warning.active 
    background-color #f0ad4e
    border-color #eea236
.btn-danger 
    color #fff
    background-color #d9534f
    border-color #d43f3a
    .badge 
        color #d9534f
        background-color #fff
.btn-danger:hover,
.btn-danger:focus,
.btn-danger:active,
.btn-danger.active,
.open > .dropdown-toggle.btn-danger 
    color #fff
    background-color #c9302c
    border-color #ac2925
.btn-danger:active,
.btn-danger.active,
.open > .dropdown-toggle.btn-danger 
    background-image none
.btn-danger.disabled,
.btn-danger[disabled],
fieldset[disabled] .btn-danger,
.btn-danger.disabled:hover,
.btn-danger[disabled]:hover,
fieldset[disabled] .btn-danger:hover,
.btn-danger.disabled:focus,
.btn-danger[disabled]:focus,
fieldset[disabled] .btn-danger:focus,
.btn-danger.disabled:active,
.btn-danger[disabled]:active,
fieldset[disabled] .btn-danger:active,
.btn-danger.disabled.active,
.btn-danger[disabled].active,
fieldset[disabled] .btn-danger.active 
    background-color #d9534f
    border-color #d43f3a
.btn-link 
    font-weight normal
    color #428bca
    cursor pointer
    border-radius 0
.btn-link,
.btn-link:active,
.btn-link[disabled],
fieldset[disabled] .btn-link 
    background-color transparent
    -webkit-box-shadow none
    box-shadow none
.btn-link,
.btn-link:hover,
.btn-link:focus,
.btn-link:active 
    border-color transparent
.btn-link:hover,
.btn-link:focus 
    color #2a6496
    text-decoration underline
    background-color transparent
.btn-link[disabled]:hover,
fieldset[disabled] .btn-link:hover,
.btn-link[disabled]:focus,
fieldset[disabled] .btn-link:focus 
    color #777
    text-decoration none
.btn-lg,
.btn-group-lg > .btn 
    padding 10px 16px
    font-size 18px
    line-height 1.33
    border-radius 6px
.btn-sm,
.btn-group-sm > .btn 
    padding 5px 10px
    font-size 12px
    line-height 1.5
    border-radius 3px
.btn-xs,
.btn-group-xs > .btn 
    padding 1px 5px
    font-size 12px
    line-height 1.5
    border-radius 3px
.btn-block 
    display block
    width 100%
    & + .btn-block 
        margin-top 5px
input[type="submit"].btn-block,
input[type="reset"].btn-block,
input[type="button"].btn-block 
    width 100%
.fade 
    opacity 0
    -webkit-transition opacity .15s linear
    -o-transition opacity .15s linear
    transition opacity .15s linear
    &.in 
        opacity 1
.collapse 
    display none
    &.in 
        display block
tr 
    &.collapse 
        &.in 
            display table-row
    &.visible-xs 
        display table-row !important
    &.visible-sm 
        display table-row !important
    &.visible-md 
        display table-row !important
    &.visible-lg 
        display table-row !important
    &.visible-print 
        display table-row !important
tbody 
    &.collapse 
        &.in 
            display table-row-group
.collapsing 
    position relative
    height 0
    overflow hidden
    -webkit-transition height .35s ease
    -o-transition height .35s ease
    transition height .35s ease
.caret 
    display inline-block
    width 0
    height 0
    margin-left 2px
    vertical-align middle
    border-top 4px solid
    border-right 4px solid transparent
    border-left 4px solid transparent
.dropdown 
    position relative
.dropdown-toggle 
    &:focus 
        outline 0
.dropdown-menu 
    position absolute
    top 100%
    left 0
    z-index 1000
    display none
    float left
    min-width 160px
    padding 5px 0
    margin 2px 0 0
    font-size 14px
    text-align left
    list-style none
    background-color #fff
    -webkit-background-clip padding-box
    background-clip padding-box
    border 1px solid #ccc
    border 1px solid rgba(0, 0, 0, .15)
    border-radius 4px
    -webkit-box-shadow 0 6px 12px rgba(0, 0, 0, .175)
    box-shadow 0 6px 12px rgba(0, 0, 0, .175)
    &.pull-right 
        right 0
        left auto
    .divider 
        height 1px
        margin 9px 0
        overflow hidden
        background-color #e5e5e5
    & > li 
        & > a 
            display block
            padding 3px 20px
            clear both
            font-weight normal
            line-height 1.42857143
            color #333
            white-space nowrap
.dropdown-menu > li > a:hover,
.dropdown-menu > li > a:focus 
    color #262626
    text-decoration none
    background-color #f5f5f5
.dropdown-menu > .active > a,
.dropdown-menu > .active > a:hover,
.dropdown-menu > .active > a:focus 
    color #fff
    text-decoration none
    background-color #428bca
    outline 0
.dropdown-menu > .disabled > a,
.dropdown-menu > .disabled > a:hover,
.dropdown-menu > .disabled > a:focus 
    color #777
.dropdown-menu > .disabled > a:hover,
.dropdown-menu > .disabled > a:focus 
    text-decoration none
    cursor not-allowed
    background-color transparent
    background-image none
    filter progid:DXImageTransform.Microsoft.gradient(enabled = false)
.open 
    & > .dropdown-menu 
        display block
    & > a 
        outline 0
.dropdown-menu-right 
    right 0
    left auto
.dropdown-menu-left 
    right auto
    left 0
.dropdown-header 
    display block
    padding 3px 20px
    font-size 12px
    line-height 1.42857143
    color #777
    white-space nowrap
.dropdown-backdrop 
    position fixed
    top 0
    right 0
    bottom 0
    left 0
    z-index 990
.pull-right 
    float right !important
    & > .dropdown-menu 
        right 0
        left auto
.dropup .caret,
.navbar-fixed-bottom .dropdown .caret 
    content ""
    border-top 0
    border-bottom 4px solid
.dropup .dropdown-menu,
.navbar-fixed-bottom .dropdown .dropdown-menu 
    top auto
    bottom 100%
    margin-bottom 1px
.navbar-right 
    float right !important
    .dropdown-menu-left 
        right auto
        left 0
}
.btn-group,
.btn-group-vertical 
    position relative
    display inline-block
    vertical-align middle
.btn-group > .btn,
.btn-group-vertical > .btn 
    position relative
    float left
.btn-group > .btn:hover,
.btn-group-vertical > .btn:hover,
.btn-group > .btn:focus,
.btn-group-vertical > .btn:focus,
.btn-group > .btn:active,
.btn-group-vertical > .btn:active,
.btn-group > .btn.active,
.btn-group-vertical > .btn.active 
    z-index 2
.btn-group > .btn:focus,
.btn-group-vertical > .btn:focus 
    outline 0
.btn-group .btn + .btn,
.btn-group .btn + .btn-group,
.btn-group .btn-group + .btn,
.btn-group .btn-group + .btn-group 
    margin-left -1px
.btn-toolbar 
    margin-left -5px
.btn-toolbar .btn-group,
.btn-toolbar .input-group 
    float left
.btn-toolbar > .btn,
.btn-toolbar > .btn-group,
.btn-toolbar > .input-group 
    margin-left 5px
.btn-group 
    & > .btn 
        &:not(:first-child):not(:last-child):not(.dropdown-toggle) 
            border-radius 0
        &:first-child 
            margin-left 0
            &:not(:last-child):not(.dropdown-toggle) 
                border-top-right-radius 0
                border-bottom-right-radius 0
        & + .dropdown-toggle 
            padding-right 8px
            padding-left 8px
    & > .btn-group 
        float left
        &:not(:first-child):not(:last-child) 
            & > .btn 
                border-radius 0
        &:last-child 
            & > .btn 
                &:first-child 
                    border-top-left-radius 0
                    border-bottom-left-radius 0
    & > .btn-lg 
        & + .dropdown-toggle 
            padding-right 12px
            padding-left 12px
    &.open 
        .dropdown-toggle 
            -webkit-box-shadow inset 0 3px 5px rgba(0, 0, 0, .125)
            box-shadow inset 0 3px 5px rgba(0, 0, 0, .125)
            &.btn-link 
                -webkit-box-shadow none
                box-shadow none
.btn-group > .btn:last-child:not(:first-child),
.btn-group > .dropdown-toggle:not(:first-child) 
    border-top-left-radius 0
    border-bottom-left-radius 0
.btn-group > .btn-group:first-child > .btn:last-child,
.btn-group > .btn-group:first-child > .dropdown-toggle 
    border-top-right-radius 0
    border-bottom-right-radius 0
.btn-group .dropdown-toggle:active,
.btn-group.open .dropdown-toggle 
    outline 0
.btn 
    .caret 
        margin-left 0
    .label 
        position relative
        top -1px
    .badge 
        position relative
        top -1px
.btn-lg 
    .caret 
        border-width 5px 5px 0
        border-bottom-width 0
.dropup 
    .btn-lg 
        .caret 
            border-width 0 5px 5px
.btn-group-vertical > .btn,
.btn-group-vertical > .btn-group,
.btn-group-vertical > .btn-group > .btn 
    display block
    float none
    width 100%
    max-width 100%
.btn-group-vertical 
    & > .btn-group 
        & > .btn 
            float none
        &:not(:first-child):not(:last-child) 
            & > .btn 
                border-radius 0
        &:last-child 
            &:not(:first-child) 
                & > .btn 
                    &:first-child 
                        border-top-left-radius 0
                        border-top-right-radius 0
    & > .btn 
        &:not(:first-child):not(:last-child) 
            border-radius 0
        &:first-child 
            &:not(:last-child) 
                border-top-right-radius 4px
                border-bottom-right-radius 0
                border-bottom-left-radius 0
        &:last-child 
            &:not(:first-child) 
                border-top-left-radius 0
                border-top-right-radius 0
                border-bottom-left-radius 4px
.btn-group-vertical > .btn + .btn,
.btn-group-vertical > .btn + .btn-group,
.btn-group-vertical > .btn-group + .btn,
.btn-group-vertical > .btn-group + .btn-group 
    margin-top -1px
    margin-left 0
.btn-group-vertical > .btn-group:first-child:not(:last-child) > .btn:last-child,
.btn-group-vertical > .btn-group:first-child:not(:last-child) > .dropdown-toggle 
    border-bottom-right-radius 0
    border-bottom-left-radius 0
.btn-group-justified 
    display table
    width 100%
    table-layout fixed
    border-collapse separate
    & > .btn-group 
        .btn 
            width 100%
        .dropdown-menu 
            left auto
.btn-group-justified > .btn,
.btn-group-justified > .btn-group 
    display table-cell
    float none
    width 1%
[data-toggle="buttons"] > .btn > input[type="radio"],
[data-toggle="buttons"] > .btn > input[type="checkbox"] 
    position absolute
    z-index -1
    filter alpha(opacity=0)
    opacity 0
.input-group 
    position relative
    display table
    border-collapse separate
    .form-control 
        position relative
        z-index 2
        float left
        width 100%
        margin-bottom 0
.input-group[class*="col-"] 
    float none
    padding-right 0
    padding-left 0
.input-group-lg > .form-control,
.input-group-lg > .input-group-addon,
.input-group-lg > .input-group-btn > .btn 
    height 46px
    padding 10px 16px
    font-size 18px
    line-height 1.33
    border-radius 6px
select.input-group-lg > .form-control,
select.input-group-lg > .input-group-addon,
select.input-group-lg > .input-group-btn > .btn 
    height 46px
    line-height 46px
textarea.input-group-lg > .form-control,
textarea.input-group-lg > .input-group-addon,
textarea.input-group-lg > .input-group-btn > .btn,
select[multiple].input-group-lg > .form-control,
select[multiple].input-group-lg > .input-group-addon,
select[multiple].input-group-lg > .input-group-btn > .btn 
    height auto
.input-group-sm > .form-control,
.input-group-sm > .input-group-addon,
.input-group-sm > .input-group-btn > .btn 
    height 30px
    padding 5px 10px
    font-size 12px
    line-height 1.5
    border-radius 3px
select.input-group-sm > .form-control,
select.input-group-sm > .input-group-addon,
select.input-group-sm > .input-group-btn > .btn 
    height 30px
    line-height 30px
textarea.input-group-sm > .form-control,
textarea.input-group-sm > .input-group-addon,
textarea.input-group-sm > .input-group-btn > .btn,
select[multiple].input-group-sm > .form-control,
select[multiple].input-group-sm > .input-group-addon,
select[multiple].input-group-sm > .input-group-btn > .btn 
    height auto
.input-group-addon,
.input-group-btn,
.input-group .form-control 
    display table-cell
.input-group-addon:not(:first-child):not(:last-child),
.input-group-btn:not(:first-child):not(:last-child),
.input-group .form-control:not(:first-child):not(:last-child) 
    border-radius 0
.input-group-addon,
.input-group-btn 
    width 1%
    white-space nowrap
    vertical-align middle
.input-group-addon 
    padding 6px 12px
    font-size 14px
    font-weight normal
    line-height 1
    color #555
    text-align center
    background-color #eee
    border 1px solid #ccc
    border-radius 4px
    &.input-sm 
        padding 5px 10px
        font-size 12px
        border-radius 3px
    &.input-lg 
        padding 10px 16px
        font-size 18px
        border-radius 6px
    &:first-child 
        border-right 0
    &:last-child 
        border-left 0
.input-group-addon input[type="radio"],
.input-group-addon input[type="checkbox"] 
    margin-top 0
.input-group .form-control:first-child,
.input-group-addon:first-child,
.input-group-btn:first-child > .btn,
.input-group-btn:first-child > .btn-group > .btn,
.input-group-btn:first-child > .dropdown-toggle,
.input-group-btn:last-child > .btn:not(:last-child):not(.dropdown-toggle),
.input-group-btn:last-child > .btn-group:not(:last-child) > .btn 
    border-top-right-radius 0
    border-bottom-right-radius 0
.input-group .form-control:last-child,
.input-group-addon:last-child,
.input-group-btn:last-child > .btn,
.input-group-btn:last-child > .btn-group > .btn,
.input-group-btn:last-child > .dropdown-toggle,
.input-group-btn:first-child > .btn:not(:first-child),
.input-group-btn:first-child > .btn-group:not(:first-child) > .btn 
    border-top-left-radius 0
    border-bottom-left-radius 0
.input-group-btn 
    position relative
    font-size 0
    white-space nowrap
    & > .btn 
        position relative
        & + .btn 
            margin-left -1px
.input-group-btn > .btn:hover,
.input-group-btn > .btn:focus,
.input-group-btn > .btn:active 
    z-index 2
.input-group-btn:first-child > .btn,
.input-group-btn:first-child > .btn-group 
    margin-right -1px
.input-group-btn:last-child > .btn,
.input-group-btn:last-child > .btn-group 
    margin-left -1px
.nav 
    padding-left 0
    margin-bottom 0
    list-style none
    & > li 
        position relative
        display block
        & > a 
            position relative
            display block
            padding 10px 15px
            & > img 
                max-width none
        &.disabled 
            & > a 
                color #777
    .nav-divider 
        height 1px
        margin 9px 0
        overflow hidden
        background-color #e5e5e5
.nav > li > a:hover,
.nav > li > a:focus 
    text-decoration none
    background-color #eee
.nav > li.disabled > a:hover,
.nav > li.disabled > a:focus 
    color #777
    text-decoration none
    cursor not-allowed
    background-color transparent
.nav .open > a,
.nav .open > a:hover,
.nav .open > a:focus 
    background-color #eee
    border-color #428bca
.nav-tabs 
    border-bottom 1px solid #ddd
    & > li 
        float left
        margin-bottom -1px
        & > a 
            margin-right 2px
            line-height 1.42857143
            border 1px solid transparent
            border-radius 4px 4px 0 0
            &:hover 
                border-color #eee #eee #ddd
    &.nav-justified 
        width 100%
        border-bottom 0
        & > li 
            float none
            & > a 
                margin-bottom 5px
                text-align center
                margin-bottom 0
        & > .dropdown 
            .dropdown-menu 
                top auto
                left auto
    .dropdown-menu 
        margin-top -1px
        border-top-left-radius 0
        border-top-right-radius 0
.nav-tabs > li.active > a,
.nav-tabs > li.active > a:hover,
.nav-tabs > li.active > a:focus 
    color #555
    cursor default
    background-color #fff
    border 1px solid #ddd
    border-bottom-color transparent
.nav-tabs.nav-justified > .active > a,
.nav-tabs.nav-justified > .active > a:hover,
.nav-tabs.nav-justified > .active > a:focus 
    border 1px solid #ddd
.nav-tabs.nav-justified > .active > a,
  .nav-tabs.nav-justified > .active > a:hover,
  .nav-tabs.nav-justified > .active > a:focus 
    border-bottom-color #fff
.nav-pills 
    & > li 
        & > a 
            border-radius 4px
            & > .badge 
                margin-left 3px
        & + li 
            margin-left 2px
.nav-pills > li.active > a,
.nav-pills > li.active > a:hover,
.nav-pills > li.active > a:focus 
    color #fff
    background-color #428bca
.nav-stacked 
    & > li 
        float none
        & + li 
            margin-top 2px
            margin-left 0
.nav-justified 
    width 100%
    & > li 
        float none
        & > a 
            margin-bottom 5px
            text-align center
            margin-bottom 0
    & > .dropdown 
        .dropdown-menu 
            top auto
            left auto
.nav-tabs-justified 
    & > li 
        & > a 
            margin-right 0
            border-radius 4px
.nav-tabs-justified > .active > a,
.nav-tabs-justified > .active > a:hover,
.nav-tabs-justified > .active > a:focus 
    border 1px solid #ddd
.nav-tabs-justified > .active > a,
  .nav-tabs-justified > .active > a:hover,
  .nav-tabs-justified > .active > a:focus 
    border-bottom-color #fff
.tab-content 
    & > .active 
        display block
.navbar-collapse 
    &.in 
        overflow-y auto
        overflow-y visible
    &.collapse 
        display block !important
        height auto !important
        padding-bottom 0
        overflow visible !important
.navbar-fixed-top .navbar-collapse,
  .navbar-static-top .navbar-collapse,
  .navbar-fixed-bottom .navbar-collapse 
    padding-right 0
    padding-left 0
}
.navbar-fixed-top .navbar-collapse,
.navbar-fixed-bottom .navbar-collapse 
    max-height 340px
}
.container > .navbar-header,
.container-fluid > .navbar-header,
.container > .navbar-collapse,
.container-fluid > .navbar-collapse 
    margin-right -15px
    margin-left -15px
}
.navbar-fixed-top,
.navbar-fixed-bottom 
    position fixed
    right 0
    left 0
    z-index 1030
    -webkit-transform translate3d(0, 0, 0)
    -o-transform translate3d(0, 0, 0)
    transform translate3d(0, 0, 0)
.navbar-fixed-bottom 
    bottom 0
    margin-bottom 0
    border-width 1px 0 0
    .navbar-nav 
        & > li 
            & > .dropdown-menu 
                border-bottom-right-radius 0
                border-bottom-left-radius 0
.navbar-brand 
    float left
    height 50px
    padding 15px 15px
    font-size 18px
    line-height 20px
.navbar-brand:hover,
.navbar-brand:focus 
    text-decoration none
.navbar-toggle 
    &:focus 
        outline 0
    .icon-bar 
        display block
        width 22px
        height 2px
        border-radius 1px
        & + .icon-bar 
            margin-top 4px
.navbar-nav 
    & > li 
        float left
        & > a 
            padding-top 10px
            padding-bottom 10px
            line-height 20px
            padding-top 15px
            padding-bottom 15px
    .open 
        .dropdown-menu 
            & > li 
                & > a 
                    line-height 20px
    &.navbar-right 
        &:last-child 
            margin-right -15px
.navbar-nav .open .dropdown-menu > li > a,
  .navbar-nav .open .dropdown-menu .dropdown-header 
    padding 5px 15px 5px 25px
.navbar-nav .open .dropdown-menu > li > a:hover,
  .navbar-nav .open .dropdown-menu > li > a:focus 
    background-image none
.navbar-form 
    .form-control 
        display inline-block
        width auto
        vertical-align middle
    .input-group 
        display inline-table
        vertical-align middle
        & > .form-control 
            width 100%
    .control-label 
        margin-bottom 0
        vertical-align middle
    .has-feedback 
        .form-control-feedback 
            top 0
    &.navbar-right 
        &:last-child 
            margin-right -15px
.navbar-form .input-group .input-group-addon,
  .navbar-form .input-group .input-group-btn,
  .navbar-form .input-group .form-control 
    width auto
.navbar-form .radio,
  .navbar-form .checkbox 
    display inline-block
    margin-top 0
    margin-bottom 0
    vertical-align middle
.navbar-form .radio label,
  .navbar-form .checkbox label 
    padding-left 0
.navbar-form .radio input[type="radio"],
  .navbar-form .checkbox input[type="checkbox"] 
    position relative
    margin-left 0
.navbar-btn 
    margin-top 8px
    margin-bottom 8px
    &.btn-sm 
        margin-top 10px
        margin-bottom 10px
    &.btn-xs 
        margin-top 14px
        margin-bottom 14px
.navbar-text 
    margin-top 15px
    margin-bottom 15px
    &.navbar-right 
        &:last-child 
            margin-right 0
.navbar-default 
    .navbar-brand 
        color #777
    .navbar-text 
        color #777
    .navbar-nav 
        & > li 
            & > a 
                color #777
    .navbar-toggle 
        border-color #ddd
        .icon-bar 
            background-color #888
    .navbar-link 
        &:hover 
            color #333
    .btn-link 
        color #777
.navbar-default .navbar-brand:hover,
.navbar-default .navbar-brand:focus 
    color #5e5e5e
    background-color transparent
.navbar-default .navbar-nav > li > a:hover,
.navbar-default .navbar-nav > li > a:focus 
    color #333
    background-color transparent
.navbar-default .navbar-nav > .active > a,
.navbar-default .navbar-nav > .active > a:hover,
.navbar-default .navbar-nav > .active > a:focus 
    color #555
    background-color #e7e7e7
.navbar-default .navbar-nav > .disabled > a,
.navbar-default .navbar-nav > .disabled > a:hover,
.navbar-default .navbar-nav > .disabled > a:focus 
    color #ccc
    background-color transparent
.navbar-default .navbar-toggle:hover,
.navbar-default .navbar-toggle:focus 
    background-color #ddd
.navbar-default .navbar-collapse,
.navbar-default .navbar-form 
    border-color #e7e7e7
.navbar-default .navbar-nav > .open > a,
.navbar-default .navbar-nav > .open > a:hover,
.navbar-default .navbar-nav > .open > a:focus 
    color #555
    background-color #e7e7e7
.navbar-default .navbar-nav .open .dropdown-menu > li > a:hover,
  .navbar-default .navbar-nav .open .dropdown-menu > li > a:focus 
    color #333
    background-color transparent
.navbar-default .navbar-nav .open .dropdown-menu > .active > a,
  .navbar-default .navbar-nav .open .dropdown-menu > .active > a:hover,
  .navbar-default .navbar-nav .open .dropdown-menu > .active > a:focus 
    color #555
    background-color #e7e7e7
.navbar-default .navbar-nav .open .dropdown-menu > .disabled > a,
  .navbar-default .navbar-nav .open .dropdown-menu > .disabled > a:hover,
  .navbar-default .navbar-nav .open .dropdown-menu > .disabled > a:focus 
    color #ccc
    background-color transparent
.navbar-default .btn-link:hover,
.navbar-default .btn-link:focus 
    color #333
.navbar-default .btn-link[disabled]:hover,
fieldset[disabled] .navbar-default .btn-link:hover,
.navbar-default .btn-link[disabled]:focus,
fieldset[disabled] .navbar-default .btn-link:focus 
    color #ccc
.navbar-inverse 
    background-color #222
    border-color #080808
    .navbar-brand 
        color #777
    .navbar-text 
        color #777
    .navbar-nav 
        & > li 
            & > a 
                color #777
        .open 
            .dropdown-menu 
                .divider 
                    background-color #080808
                & > li 
                    & > a 
                        color #777
    .navbar-toggle 
        border-color #333
        .icon-bar 
            background-color #fff
    .navbar-link 
        &:hover 
            color #fff
    .btn-link 
        color #777
.navbar-inverse .navbar-brand:hover,
.navbar-inverse .navbar-brand:focus 
    color #fff
    background-color transparent
.navbar-inverse .navbar-nav > li > a:hover,
.navbar-inverse .navbar-nav > li > a:focus 
    color #fff
    background-color transparent
.navbar-inverse .navbar-nav > .active > a,
.navbar-inverse .navbar-nav > .active > a:hover,
.navbar-inverse .navbar-nav > .active > a:focus 
    color #fff
    background-color #080808
.navbar-inverse .navbar-nav > .disabled > a,
.navbar-inverse .navbar-nav > .disabled > a:hover,
.navbar-inverse .navbar-nav > .disabled > a:focus 
    color #444
    background-color transparent
.navbar-inverse .navbar-toggle:hover,
.navbar-inverse .navbar-toggle:focus 
    background-color #333
.navbar-inverse .navbar-collapse,
.navbar-inverse .navbar-form 
    border-color #101010
.navbar-inverse .navbar-nav > .open > a,
.navbar-inverse .navbar-nav > .open > a:hover,
.navbar-inverse .navbar-nav > .open > a:focus 
    color #fff
    background-color #080808
.navbar-inverse .navbar-nav .open .dropdown-menu > li > a:hover,
  .navbar-inverse .navbar-nav .open .dropdown-menu > li > a:focus 
    color #fff
    background-color transparent
.navbar-inverse .navbar-nav .open .dropdown-menu > .active > a,
  .navbar-inverse .navbar-nav .open .dropdown-menu > .active > a:hover,
  .navbar-inverse .navbar-nav .open .dropdown-menu > .active > a:focus 
    color #fff
    background-color #080808
.navbar-inverse .navbar-nav .open .dropdown-menu > .disabled > a,
  .navbar-inverse .navbar-nav .open .dropdown-menu > .disabled > a:hover,
  .navbar-inverse .navbar-nav .open .dropdown-menu > .disabled > a:focus 
    color #444
    background-color transparent
.navbar-inverse .btn-link:hover,
.navbar-inverse .btn-link:focus 
    color #fff
.navbar-inverse .btn-link[disabled]:hover,
fieldset[disabled] .navbar-inverse .btn-link:hover,
.navbar-inverse .btn-link[disabled]:focus,
fieldset[disabled] .navbar-inverse .btn-link:focus 
    color #444
.breadcrumb 
    padding 8px 15px
    margin-bottom 20px
    list-style none
    background-color #f5f5f5
    border-radius 4px
    & > li 
        display inline-block
        & + li 
            &:before 
                padding 0 5px
                color #ccc
                content "/\00a0"
    & > .active 
        color #777
.pagination 
    display inline-block
    padding-left 0
    margin 20px 0
    border-radius 4px
    & > li 
        display inline
.pagination > li > a,
.pagination > li > span 
    position relative
    float left
    padding 6px 12px
    margin-left -1px
    line-height 1.42857143
    color #428bca
    text-decoration none
    background-color #fff
    border 1px solid #ddd
.pagination > li:first-child > a,
.pagination > li:first-child > span 
    margin-left 0
    border-top-left-radius 4px
    border-bottom-left-radius 4px
.pagination > li:last-child > a,
.pagination > li:last-child > span 
    border-top-right-radius 4px
    border-bottom-right-radius 4px
.pagination > li > a:hover,
.pagination > li > span:hover,
.pagination > li > a:focus,
.pagination > li > span:focus 
    color #2a6496
    background-color #eee
    border-color #ddd
.pagination > .active > a,
.pagination > .active > span,
.pagination > .active > a:hover,
.pagination > .active > span:hover,
.pagination > .active > a:focus,
.pagination > .active > span:focus 
    z-index 2
    color #fff
    cursor default
    background-color #428bca
    border-color #428bca
.pagination > .disabled > span,
.pagination > .disabled > span:hover,
.pagination > .disabled > span:focus,
.pagination > .disabled > a,
.pagination > .disabled > a:hover,
.pagination > .disabled > a:focus 
    color #777
    cursor not-allowed
    background-color #fff
    border-color #ddd
.pagination-lg > li > a,
.pagination-lg > li > span 
    padding 10px 16px
    font-size 18px
.pagination-lg > li:first-child > a,
.pagination-lg > li:first-child > span 
    border-top-left-radius 6px
    border-bottom-left-radius 6px
.pagination-lg > li:last-child > a,
.pagination-lg > li:last-child > span 
    border-top-right-radius 6px
    border-bottom-right-radius 6px
.pagination-sm > li > a,
.pagination-sm > li > span 
    padding 5px 10px
    font-size 12px
.pagination-sm > li:first-child > a,
.pagination-sm > li:first-child > span 
    border-top-left-radius 3px
    border-bottom-left-radius 3px
.pagination-sm > li:last-child > a,
.pagination-sm > li:last-child > span 
    border-top-right-radius 3px
    border-bottom-right-radius 3px
.pager 
    padding-left 0
    margin 20px 0
    text-align center
    list-style none
    li 
        display inline
.pager li > a,
.pager li > span 
    display inline-block
    padding 5px 14px
    background-color #fff
    border 1px solid #ddd
    border-radius 15px
.pager li > a:hover,
.pager li > a:focus 
    text-decoration none
    background-color #eee
.pager .next > a,
.pager .next > span 
    float right
.pager .previous > a,
.pager .previous > span 
    float left
.pager .disabled > a,
.pager .disabled > a:hover,
.pager .disabled > a:focus,
.pager .disabled > span 
    color #777
    cursor not-allowed
    background-color #fff
a.label:hover,
a.label:focus 
    color #fff
    text-decoration none
    cursor pointer
.label-default 
    background-color #777
.label-default[href]:hover,
.label-default[href]:focus 
    background-color #5e5e5e
.label-primary 
    background-color #428bca
.label-primary[href]:hover,
.label-primary[href]:focus 
    background-color #3071a9
.label-success 
    background-color #5cb85c
.label-success[href]:hover,
.label-success[href]:focus 
    background-color #449d44
.label-info 
    background-color #5bc0de
.label-info[href]:hover,
.label-info[href]:focus 
    background-color #31b0d5
.label-warning 
    background-color #f0ad4e
.label-warning[href]:hover,
.label-warning[href]:focus 
    background-color #ec971f
.label-danger 
    background-color #d9534f
.label-danger[href]:hover,
.label-danger[href]:focus 
    background-color #c9302c
.badge 
    display inline-block
    min-width 10px
    padding 3px 7px
    font-size 12px
    font-weight bold
    line-height 1
    color #fff
    text-align center
    white-space nowrap
    vertical-align baseline
    background-color #777
    border-radius 10px
    &:empty 
        display none
.btn-xs 
    .badge 
        top 0
        padding 1px 5px
a.badge:hover,
a.badge:focus 
    color #fff
    text-decoration none
    cursor pointer
a.list-group-item.active > .badge,
.nav-pills > .active > a > .badge 
    color #428bca
    background-color #fff
.jumbotron 
    padding 30px
    margin-bottom 30px
    color inherit
    background-color #eee
    p 
        margin-bottom 15px
        font-size 21px
        font-weight 200
    & > hr 
        border-top-color #d5d5d5
    .container 
        max-width 100%
.jumbotron h1,
.jumbotron .h1 
    color inherit
.jumbotron h1,
  .jumbotron .h1 
    font-size 63px
.thumbnail > img,
.thumbnail a > img 
    margin-right auto
    margin-left auto
a.thumbnail:hover,
a.thumbnail:focus,
a.thumbnail.active 
    border-color #428bca
.thumbnail 
    .caption 
        padding 9px
        color #333
.alert 
    padding 15px
    margin-bottom 20px
    border 1px solid transparent
    border-radius 4px
    h4 
        margin-top 0
        color inherit
    .alert-link 
        font-weight bold
    & > p 
        & + p 
            margin-top 5px
.alert > p,
.alert > ul 
    margin-bottom 0
.alert-dismissable,
.alert-dismissible 
    padding-right 35px
.alert-dismissable .close,
.alert-dismissible .close 
    position relative
    top -2px
    right -21px
    color inherit
.alert-success 
    color #3c763d
    background-color #dff0d8
    border-color #d6e9c6
    hr 
        border-top-color #c9e2b3
    .alert-link 
        color #2b542c
.alert-info 
    color #31708f
    background-color #d9edf7
    border-color #bce8f1
    hr 
        border-top-color #a6e1ec
    .alert-link 
        color #245269
.alert-warning 
    color #8a6d3b
    background-color #fcf8e3
    border-color #faebcc
    hr 
        border-top-color #f7e1b5
    .alert-link 
        color #66512c
.alert-danger 
    color #a94442
    background-color #f2dede
    border-color #ebccd1
    hr 
        border-top-color #e4b9c0
    .alert-link 
        color #843534
@-webkit-keyframes 
    progress-bar-stripes 
        from {
    background-position 40px 0
to 
    background-position 0 0
    background-position 0 0
    background-position 0 0
.progress-bar 
    float left
    width 0
    height 100%
    font-size 12px
    line-height 20px
    color #fff
    text-align center
    background-color #428bca
    -webkit-box-shadow inset 0 -1px 0 rgba(0, 0, 0, .15)
    box-shadow inset 0 -1px 0 rgba(0, 0, 0, .15)
    -webkit-transition width .6s ease
    -o-transition width .6s ease
    transition width .6s ease
.progress-striped .progress-bar,
.progress-bar-striped 
    background-image -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
    background-image -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
    background-image linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
    -webkit-background-size 40px 40px
    background-size 40px 40px
.progress.active .progress-bar,
.progress-bar.active 
    -webkit-animation progress-bar-stripes 2s linear infinite
    -o-animation progress-bar-stripes 2s linear infinite
    animation progress-bar-stripes 2s linear infinite
.progress-bar[aria-valuenow="1"],
.progress-bar[aria-valuenow="2"] 
    min-width 30px
.progress-bar[aria-valuenow="0"] 
    min-width 30px
    color #777
    background-color transparent
    background-image none
    -webkit-box-shadow none
    box-shadow none
.progress-bar-success 
    background-color #5cb85c
.progress-striped 
    .progress-bar-success 
        background-image -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
        background-image -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
        background-image linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
    .progress-bar-info 
        background-image -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
        background-image -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
        background-image linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
    .progress-bar-warning 
        background-image -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
        background-image -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
        background-image linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
    .progress-bar-danger 
        background-image -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
        background-image -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
        background-image linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
.progress-bar-info 
    background-color #5bc0de
.progress-bar-warning 
    background-color #f0ad4e
.progress-bar-danger 
    background-color #d9534f
.media,
.media-body 
    overflow hidden
    zoom 1
.media,
.media .media 
    margin-top 15px
.media 
    &:first-child 
        margin-top 0
    & > .pull-left 
        margin-right 10px
    & > .pull-right 
        margin-left 10px
.media-object 
    display block
.media-heading 
    margin 0 0 5px
.media-list 
    padding-left 0
    list-style none
.list-group 
    padding-left 0
    margin-bottom 20px
    & + .panel-footer 
        border-top-width 0
.list-group-item 
    position relative
    display block
    padding 10px 15px
    margin-bottom -1px
    background-color #fff
    border 1px solid #ddd
    &:first-child 
        border-top-left-radius 4px
        border-top-right-radius 4px
    &:last-child 
        margin-bottom 0
        border-bottom-right-radius 4px
        border-bottom-left-radius 4px
    & > .badge 
        float right
        & + .badge 
            margin-right 5px
a.list-group-item:hover,
a.list-group-item:focus 
    color #555
    text-decoration none
    background-color #f5f5f5
.list-group-item.disabled,
.list-group-item.disabled:hover,
.list-group-item.disabled:focus 
    color #777
    background-color #eee
.list-group-item.disabled .list-group-item-heading,
.list-group-item.disabled:hover .list-group-item-heading,
.list-group-item.disabled:focus .list-group-item-heading 
    color inherit
.list-group-item.disabled .list-group-item-text,
.list-group-item.disabled:hover .list-group-item-text,
.list-group-item.disabled:focus .list-group-item-text 
    color #777
.list-group-item.active,
.list-group-item.active:hover,
.list-group-item.active:focus 
    z-index 2
    color #fff
    background-color #428bca
    border-color #428bca
.list-group-item.active .list-group-item-heading,
.list-group-item.active:hover .list-group-item-heading,
.list-group-item.active:focus .list-group-item-heading,
.list-group-item.active .list-group-item-heading > small,
.list-group-item.active:hover .list-group-item-heading > small,
.list-group-item.active:focus .list-group-item-heading > small,
.list-group-item.active .list-group-item-heading > .small,
.list-group-item.active:hover .list-group-item-heading > .small,
.list-group-item.active:focus .list-group-item-heading > .small 
    color inherit
.list-group-item.active .list-group-item-text,
.list-group-item.active:hover .list-group-item-text,
.list-group-item.active:focus .list-group-item-text 
    color #e1edf7
.list-group-item-success 
    color #3c763d
    background-color #dff0d8
a.list-group-item-success:hover,
a.list-group-item-success:focus 
    color #3c763d
    background-color #d0e9c6
a.list-group-item-success.active,
a.list-group-item-success.active:hover,
a.list-group-item-success.active:focus 
    color #fff
    background-color #3c763d
    border-color #3c763d
.list-group-item-info 
    color #31708f
    background-color #d9edf7
a.list-group-item-info:hover,
a.list-group-item-info:focus 
    color #31708f
    background-color #c4e3f3
a.list-group-item-info.active,
a.list-group-item-info.active:hover,
a.list-group-item-info.active:focus 
    color #fff
    background-color #31708f
    border-color #31708f
.list-group-item-warning 
    color #8a6d3b
    background-color #fcf8e3
a.list-group-item-warning:hover,
a.list-group-item-warning:focus 
    color #8a6d3b
    background-color #faf2cc
a.list-group-item-warning.active,
a.list-group-item-warning.active:hover,
a.list-group-item-warning.active:focus 
    color #fff
    background-color #8a6d3b
    border-color #8a6d3b
.list-group-item-danger 
    color #a94442
    background-color #f2dede
a.list-group-item-danger:hover,
a.list-group-item-danger:focus 
    color #a94442
    background-color #ebcccc
a.list-group-item-danger.active,
a.list-group-item-danger.active:hover,
a.list-group-item-danger.active:focus 
    color #fff
    background-color #a94442
    border-color #a94442
.list-group-item-heading 
    margin-top 0
    margin-bottom 5px
.list-group-item-text 
    margin-bottom 0
    line-height 1.3
.panel 
    margin-bottom 20px
    background-color #fff
    border 1px solid transparent
    border-radius 4px
    -webkit-box-shadow 0 1px 1px rgba(0, 0, 0, .05)
    box-shadow 0 1px 1px rgba(0, 0, 0, .05)
    & > .list-group 
        margin-bottom 0
        .list-group-item 
            border-width 1px 0
            border-radius 0
        &:first-child 
            .list-group-item 
                &:first-child 
                    border-top 0
                    border-top-left-radius 3px
                    border-top-right-radius 3px
        &:last-child 
            .list-group-item 
                &:last-child 
                    border-bottom 0
                    border-bottom-right-radius 3px
                    border-bottom-left-radius 3px
    & > .table-responsive 
        margin-bottom 0
        border 0
.panel-body 
    padding 15px
.panel-heading 
    padding 10px 15px
    border-bottom 1px solid transparent
    border-top-left-radius 3px
    border-top-right-radius 3px
    & > .dropdown 
        .dropdown-toggle 
            color inherit
    & + .list-group 
        .list-group-item 
            &:first-child 
                border-top-width 0
.panel-title 
    margin-top 0
    margin-bottom 0
    font-size 16px
    color inherit
    & > a 
        color inherit
.panel-footer 
    padding 10px 15px
    background-color #f5f5f5
    border-top 1px solid #ddd
    border-bottom-right-radius 3px
    border-bottom-left-radius 3px
.panel > .table,
.panel > .table-responsive > .table,
.panel > .panel-collapse > .table 
    margin-bottom 0
.panel > .table:first-child,
.panel > .table-responsive:first-child > .table:first-child 
    border-top-left-radius 3px
    border-top-right-radius 3px
.panel > .table:first-child > thead:first-child > tr:first-child td:first-child,
.panel > .table-responsive:first-child > .table:first-child > thead:first-child > tr:first-child td:first-child,
.panel > .table:first-child > tbody:first-child > tr:first-child td:first-child,
.panel > .table-responsive:first-child > .table:first-child > tbody:first-child > tr:first-child td:first-child,
.panel > .table:first-child > thead:first-child > tr:first-child th:first-child,
.panel > .table-responsive:first-child > .table:first-child > thead:first-child > tr:first-child th:first-child,
.panel > .table:first-child > tbody:first-child > tr:first-child th:first-child,
.panel > .table-responsive:first-child > .table:first-child > tbody:first-child > tr:first-child th:first-child 
    border-top-left-radius 3px
.panel > .table:first-child > thead:first-child > tr:first-child td:last-child,
.panel > .table-responsive:first-child > .table:first-child > thead:first-child > tr:first-child td:last-child,
.panel > .table:first-child > tbody:first-child > tr:first-child td:last-child,
.panel > .table-responsive:first-child > .table:first-child > tbody:first-child > tr:first-child td:last-child,
.panel > .table:first-child > thead:first-child > tr:first-child th:last-child,
.panel > .table-responsive:first-child > .table:first-child > thead:first-child > tr:first-child th:last-child,
.panel > .table:first-child > tbody:first-child > tr:first-child th:last-child,
.panel > .table-responsive:first-child > .table:first-child > tbody:first-child > tr:first-child th:last-child 
    border-top-right-radius 3px
.panel > .table:last-child,
.panel > .table-responsive:last-child > .table:last-child 
    border-bottom-right-radius 3px
    border-bottom-left-radius 3px
.panel > .table:last-child > tbody:last-child > tr:last-child td:first-child,
.panel > .table-responsive:last-child > .table:last-child > tbody:last-child > tr:last-child td:first-child,
.panel > .table:last-child > tfoot:last-child > tr:last-child td:first-child,
.panel > .table-responsive:last-child > .table:last-child > tfoot:last-child > tr:last-child td:first-child,
.panel > .table:last-child > tbody:last-child > tr:last-child th:first-child,
.panel > .table-responsive:last-child > .table:last-child > tbody:last-child > tr:last-child th:first-child,
.panel > .table:last-child > tfoot:last-child > tr:last-child th:first-child,
.panel > .table-responsive:last-child > .table:last-child > tfoot:last-child > tr:last-child th:first-child 
    border-bottom-left-radius 3px
.panel > .table:last-child > tbody:last-child > tr:last-child td:last-child,
.panel > .table-responsive:last-child > .table:last-child > tbody:last-child > tr:last-child td:last-child,
.panel > .table:last-child > tfoot:last-child > tr:last-child td:last-child,
.panel > .table-responsive:last-child > .table:last-child > tfoot:last-child > tr:last-child td:last-child,
.panel > .table:last-child > tbody:last-child > tr:last-child th:last-child,
.panel > .table-responsive:last-child > .table:last-child > tbody:last-child > tr:last-child th:last-child,
.panel > .table:last-child > tfoot:last-child > tr:last-child th:last-child,
.panel > .table-responsive:last-child > .table:last-child > tfoot:last-child > tr:last-child th:last-child 
    border-bottom-right-radius 3px
.panel > .panel-body + .table,
.panel > .panel-body + .table-responsive 
    border-top 1px solid #ddd
.panel > .table > tbody:first-child > tr:first-child th,
.panel > .table > tbody:first-child > tr:first-child td 
    border-top 0
.panel > .table-bordered,
.panel > .table-responsive > .table-bordered 
    border 0
.panel > .table-bordered > thead > tr > th:first-child,
.panel > .table-responsive > .table-bordered > thead > tr > th:first-child,
.panel > .table-bordered > tbody > tr > th:first-child,
.panel > .table-responsive > .table-bordered > tbody > tr > th:first-child,
.panel > .table-bordered > tfoot > tr > th:first-child,
.panel > .table-responsive > .table-bordered > tfoot > tr > th:first-child,
.panel > .table-bordered > thead > tr > td:first-child,
.panel > .table-responsive > .table-bordered > thead > tr > td:first-child,
.panel > .table-bordered > tbody > tr > td:first-child,
.panel > .table-responsive > .table-bordered > tbody > tr > td:first-child,
.panel > .table-bordered > tfoot > tr > td:first-child,
.panel > .table-responsive > .table-bordered > tfoot > tr > td:first-child 
    border-left 0
.panel > .table-bordered > thead > tr > th:last-child,
.panel > .table-responsive > .table-bordered > thead > tr > th:last-child,
.panel > .table-bordered > tbody > tr > th:last-child,
.panel > .table-responsive > .table-bordered > tbody > tr > th:last-child,
.panel > .table-bordered > tfoot > tr > th:last-child,
.panel > .table-responsive > .table-bordered > tfoot > tr > th:last-child,
.panel > .table-bordered > thead > tr > td:last-child,
.panel > .table-responsive > .table-bordered > thead > tr > td:last-child,
.panel > .table-bordered > tbody > tr > td:last-child,
.panel > .table-responsive > .table-bordered > tbody > tr > td:last-child,
.panel > .table-bordered > tfoot > tr > td:last-child,
.panel > .table-responsive > .table-bordered > tfoot > tr > td:last-child 
    border-right 0
.panel > .table-bordered > thead > tr:first-child > td,
.panel > .table-responsive > .table-bordered > thead > tr:first-child > td,
.panel > .table-bordered > tbody > tr:first-child > td,
.panel > .table-responsive > .table-bordered > tbody > tr:first-child > td,
.panel > .table-bordered > thead > tr:first-child > th,
.panel > .table-responsive > .table-bordered > thead > tr:first-child > th,
.panel > .table-bordered > tbody > tr:first-child > th,
.panel > .table-responsive > .table-bordered > tbody > tr:first-child > th 
    border-bottom 0
.panel > .table-bordered > tbody > tr:last-child > td,
.panel > .table-responsive > .table-bordered > tbody > tr:last-child > td,
.panel > .table-bordered > tfoot > tr:last-child > td,
.panel > .table-responsive > .table-bordered > tfoot > tr:last-child > td,
.panel > .table-bordered > tbody > tr:last-child > th,
.panel > .table-responsive > .table-bordered > tbody > tr:last-child > th,
.panel > .table-bordered > tfoot > tr:last-child > th,
.panel > .table-responsive > .table-bordered > tfoot > tr:last-child > th 
    border-bottom 0
.panel-group 
    margin-bottom 20px
    .panel 
        margin-bottom 0
        border-radius 4px
        & + .panel 
            margin-top 5px
    .panel-heading 
        border-bottom 0
        & + .panel-collapse 
            & > .panel-body 
                border-top 1px solid #ddd
    .panel-footer 
        border-top 0
        & + .panel-collapse 
            .panel-body 
                border-bottom 1px solid #ddd
.panel-default 
    border-color #ddd
    & > .panel-heading 
        color #333
        background-color #f5f5f5
        border-color #ddd
        & + .panel-collapse 
            & > .panel-body 
                border-top-color #ddd
        .badge 
            color #f5f5f5
            background-color #333
    & > .panel-footer 
        & + .panel-collapse 
            & > .panel-body 
                border-bottom-color #ddd
.panel-primary 
    border-color #428bca
    & > .panel-heading 
        color #fff
        background-color #428bca
        border-color #428bca
        & + .panel-collapse 
            & > .panel-body 
                border-top-color #428bca
        .badge 
            color #428bca
            background-color #fff
    & > .panel-footer 
        & + .panel-collapse 
            & > .panel-body 
                border-bottom-color #428bca
.panel-success 
    border-color #d6e9c6
    & > .panel-heading 
        color #3c763d
        background-color #dff0d8
        border-color #d6e9c6
        & + .panel-collapse 
            & > .panel-body 
                border-top-color #d6e9c6
        .badge 
            color #dff0d8
            background-color #3c763d
    & > .panel-footer 
        & + .panel-collapse 
            & > .panel-body 
                border-bottom-color #d6e9c6
.panel-info 
    border-color #bce8f1
    & > .panel-heading 
        color #31708f
        background-color #d9edf7
        border-color #bce8f1
        & + .panel-collapse 
            & > .panel-body 
                border-top-color #bce8f1
        .badge 
            color #d9edf7
            background-color #31708f
    & > .panel-footer 
        & + .panel-collapse 
            & > .panel-body 
                border-bottom-color #bce8f1
.panel-warning 
    border-color #faebcc
    & > .panel-heading 
        color #8a6d3b
        background-color #fcf8e3
        border-color #faebcc
        & + .panel-collapse 
            & > .panel-body 
                border-top-color #faebcc
        .badge 
            color #fcf8e3
            background-color #8a6d3b
    & > .panel-footer 
        & + .panel-collapse 
            & > .panel-body 
                border-bottom-color #faebcc
.panel-danger 
    border-color #ebccd1
    & > .panel-heading 
        color #a94442
        background-color #f2dede
        border-color #ebccd1
        & + .panel-collapse 
            & > .panel-body 
                border-top-color #ebccd1
        .badge 
            color #f2dede
            background-color #a94442
    & > .panel-footer 
        & + .panel-collapse 
            & > .panel-body 
                border-bottom-color #ebccd1
.embed-responsive 
    position relative
    display block
    height 0
    padding 0
    overflow hidden
    &.embed-responsive-16by9 
        padding-bottom 56.25%
    &.embed-responsive-4by3 
        padding-bottom 75%
.embed-responsive .embed-responsive-item,
.embed-responsive iframe,
.embed-responsive embed,
.embed-responsive object 
    position absolute
    top 0
    bottom 0
    left 0
    width 100%
    height 100%
    border 0
.well 
    min-height 20px
    padding 19px
    margin-bottom 20px
    background-color #f5f5f5
    border 1px solid #e3e3e3
    border-radius 4px
    -webkit-box-shadow inset 0 1px 1px rgba(0, 0, 0, .05)
    box-shadow inset 0 1px 1px rgba(0, 0, 0, .05)
    blockquote 
        border-color #ddd
        border-color rgba(0, 0, 0, .15)
.well-lg 
    padding 24px
    border-radius 6px
.well-sm 
    padding 9px
    border-radius 3px
.close 
    float right
    font-size 21px
    font-weight bold
    line-height 1
    color #000
    text-shadow 0 1px 0 #fff
    filter alpha(opacity=20)
    opacity .2
.close:hover,
.close:focus 
    color #000
    text-decoration none
    cursor pointer
    filter alpha(opacity=50)
    opacity .5
.modal-open 
    overflow hidden
    .modal 
        overflow-x hidden
        overflow-y auto
.modal 
    position fixed
    top 0
    right 0
    bottom 0
    left 0
    z-index 1050
    display none
    overflow hidden
    -webkit-overflow-scrolling touch
    outline 0
    &.fade 
        .modal-dialog 
            -webkit-transition -webkit-transform .3s ease-out
            -o-transition -o-transform .3s ease-out
            transition transform .3s ease-out
            -webkit-transform translate3d(0, -25%, 0)
            -o-transform translate3d(0, -25%, 0)
            transform translate3d(0, -25%, 0)
    &.in 
        .modal-dialog 
            -webkit-transform translate3d(0, 0, 0)
            -o-transform translate3d(0, 0, 0)
            transform translate3d(0, 0, 0)
.modal-dialog 
    position relative
    width auto
    margin 10px
.modal-content 
    position relative
    background-color #fff
    -webkit-background-clip padding-box
    background-clip padding-box
    border 1px solid #999
    border 1px solid rgba(0, 0, 0, .2)
    border-radius 6px
    outline 0
    -webkit-box-shadow 0 3px 9px rgba(0, 0, 0, .5)
    box-shadow 0 3px 9px rgba(0, 0, 0, .5)
    -webkit-box-shadow 0 5px 15px rgba(0, 0, 0, .5)
    box-shadow 0 5px 15px rgba(0, 0, 0, .5)
.modal-backdrop 
    position fixed
    top 0
    right 0
    bottom 0
    left 0
    z-index 1040
    background-color #000
    &.fade 
        filter alpha(opacity=0)
        opacity 0
    &.in 
        filter alpha(opacity=50)
        opacity .5
.modal-header 
    min-height 16.42857143px
    padding 15px
    border-bottom 1px solid #e5e5e5
    .close 
        margin-top -2px
.modal-title 
    margin 0
    line-height 1.42857143
.modal-body 
    position relative
    padding 15px
.modal-footer 
    padding 15px
    text-align right
    border-top 1px solid #e5e5e5
    .btn 
        & + .btn 
            margin-bottom 0
            margin-left 5px
    .btn-group 
        .btn 
            & + .btn 
                margin-left -1px
    .btn-block 
        & + .btn-block 
            margin-left 0
.modal-scrollbar-measure 
    position absolute
    top -9999px
    width 50px
    height 50px
    overflow scroll
.modal-sm 
    width 300px
.tooltip 
    &.in 
        filter alpha(opacity=90)
        opacity .9
    &.top 
        padding 5px 0
        margin-top -3px
        .tooltip-arrow 
            bottom 0
            left 50%
            margin-left -5px
            border-width 5px 5px 0
            border-top-color #000
    &.right 
        padding 0 5px
        margin-left 3px
        .tooltip-arrow 
            top 50%
            left 0
            margin-top -5px
            border-width 5px 5px 5px 0
            border-right-color #000
    &.bottom 
        padding 5px 0
        margin-top 3px
        .tooltip-arrow 
            top 0
            left 50%
            margin-left -5px
            border-width 0 5px 5px
            border-bottom-color #000
    &.left 
        padding 0 5px
        margin-left -3px
        .tooltip-arrow 
            top 50%
            right 0
            margin-top -5px
            border-width 5px 0 5px 5px
            border-left-color #000
    &.top-left 
        .tooltip-arrow 
            bottom 0
            left 5px
            border-width 5px 5px 0
            border-top-color #000
    &.top-right 
        .tooltip-arrow 
            right 5px
            bottom 0
            border-width 5px 5px 0
            border-top-color #000
    &.bottom-left 
        .tooltip-arrow 
            top 0
            left 5px
            border-width 0 5px 5px
            border-bottom-color #000
    &.bottom-right 
        .tooltip-arrow 
            top 0
            right 5px
            border-width 0 5px 5px
            border-bottom-color #000
.tooltip-inner 
    max-width 200px
    padding 3px 8px
    color #fff
    text-align center
    text-decoration none
    background-color #000
    border-radius 4px
.tooltip-arrow 
    position absolute
    width 0
    height 0
    border-color transparent
    border-style solid
.popover 
    position absolute
    top 0
    left 0
    z-index 1060
    display none
    max-width 276px
    padding 1px
    text-align left
    white-space normal
    background-color #fff
    -webkit-background-clip padding-box
    background-clip padding-box
    border 1px solid #ccc
    border 1px solid rgba(0, 0, 0, .2)
    border-radius 6px
    -webkit-box-shadow 0 5px 10px rgba(0, 0, 0, .2)
    box-shadow 0 5px 10px rgba(0, 0, 0, .2)
    &.top 
        margin-top -10px
        & > .arrow 
            bottom -11px
            left 50%
            margin-left -11px
            border-top-color #999
            border-top-color rgba(0, 0, 0, .25)
            border-bottom-width 0
            &:after 
                bottom 1px
                margin-left -10px
                content " "
                border-top-color #fff
                border-bottom-width 0
    &.right 
        margin-left 10px
        & > .arrow 
            top 50%
            left -11px
            margin-top -11px
            border-right-color #999
            border-right-color rgba(0, 0, 0, .25)
            border-left-width 0
            &:after 
                bottom -10px
                left 1px
                content " "
                border-right-color #fff
                border-left-width 0
    &.bottom 
        margin-top 10px
        & > .arrow 
            top -11px
            left 50%
            margin-left -11px
            border-top-width 0
            border-bottom-color #999
            border-bottom-color rgba(0, 0, 0, .25)
            &:after 
                top 1px
                margin-left -10px
                content " "
                border-top-width 0
                border-bottom-color #fff
    &.left 
        margin-left -10px
        & > .arrow 
            top 50%
            right -11px
            margin-top -11px
            border-right-width 0
            border-left-color #999
            border-left-color rgba(0, 0, 0, .25)
            &:after 
                right 1px
                bottom -10px
                content " "
                border-right-width 0
                border-left-color #fff
    & > .arrow 
        border-width 11px
        &:after 
            content ""
            border-width 10px
.popover-title 
    padding 8px 14px
    margin 0
    font-size 14px
    font-weight normal
    line-height 18px
    background-color #f7f7f7
    border-bottom 1px solid #ebebeb
    border-radius 5px 5px 0 0
.popover-content 
    padding 9px 14px
.popover > .arrow,
.popover > .arrow:after 
    position absolute
    display block
    width 0
    height 0
    border-color transparent
    border-style solid
.carousel 
    position relative
.carousel-inner 
    position relative
    width 100%
    overflow hidden
    & > .item 
        position relative
        display none
        -webkit-transition .6s ease-in-out left
        -o-transition .6s ease-in-out left
        transition .6s ease-in-out left
    & > .active 
        left 0
        &.left 
            left -100%
        &.right 
            left 100%
    & > .next 
        left 100%
    & > .prev 
        left -100%
.carousel-inner > .item > img,
.carousel-inner > .item > a > img 
    line-height 1
.carousel-inner > .active,
.carousel-inner > .next,
.carousel-inner > .prev 
    display block
.carousel-inner > .next,
.carousel-inner > .prev 
    position absolute
    top 0
    width 100%
.carousel-inner > .next.left,
.carousel-inner > .prev.right 
    left 0
.carousel-control 
    position absolute
    top 0
    bottom 0
    left 0
    width 15%
    font-size 20px
    color #fff
    text-align center
    text-shadow 0 1px 2px rgba(0, 0, 0, .6)
    filter alpha(opacity=50)
    opacity .5
    &.left 
        background-image -webkit-linear-gradient(left, rgba(0, 0, 0, .5) 0%, rgba(0, 0, 0, .0001) 100%)
        background-image -o-linear-gradient(left, rgba(0, 0, 0, .5) 0%, rgba(0, 0, 0, .0001) 100%)
        background-image -webkit-gradient(linear, left top, right top, from(rgba(0, 0, 0, .5)), to(rgba(0, 0, 0, .0001)))
        background-image linear-gradient(to right, rgba(0, 0, 0, .5) 0%, rgba(0, 0, 0, .0001) 100%)
        filter progid:DXImageTransform.Microsoft.gradient(startColorstr='#80000000', endColorstr='#00000000', GradientType=1)
        background-repeat repeat-x
    &.right 
        right 0
        left auto
        background-image -webkit-linear-gradient(left, rgba(0, 0, 0, .0001) 0%, rgba(0, 0, 0, .5) 100%)
        background-image -o-linear-gradient(left, rgba(0, 0, 0, .0001) 0%, rgba(0, 0, 0, .5) 100%)
        background-image -webkit-gradient(linear, left top, right top, from(rgba(0, 0, 0, .0001)), to(rgba(0, 0, 0, .5)))
        background-image linear-gradient(to right, rgba(0, 0, 0, .0001) 0%, rgba(0, 0, 0, .5) 100%)
        filter progid:DXImageTransform.Microsoft.gradient(startColorstr='#00000000', endColorstr='#80000000', GradientType=1)
        background-repeat repeat-x
    .icon-prev 
        &:before 
            content '\2039'
    .icon-next 
        &:before 
            content '\203a'
.carousel-control:hover,
.carousel-control:focus 
    color #fff
    text-decoration none
    filter alpha(opacity=90)
    outline 0
    opacity .9
.carousel-control .icon-prev,
.carousel-control .icon-next,
.carousel-control .glyphicon-chevron-left,
.carousel-control .glyphicon-chevron-right 
    position absolute
    top 50%
    z-index 5
    display inline-block
.carousel-control .icon-prev,
.carousel-control .glyphicon-chevron-left 
    left 50%
    margin-left -10px
.carousel-control .icon-next,
.carousel-control .glyphicon-chevron-right 
    right 50%
    margin-right -10px
.carousel-control .icon-prev,
.carousel-control .icon-next 
    width 20px
    height 20px
    margin-top -10px
    font-family serif
.carousel-indicators 
    position absolute
    bottom 10px
    left 50%
    z-index 15
    width 60%
    padding-left 0
    margin-left -30%
    text-align center
    list-style none
    bottom 20px
    li 
        display inline-block
        width 10px
        height 10px
        margin 1px
        text-indent -999px
        cursor pointer
        background-color #000 \9
        background-color rgba(0, 0, 0, 0)
        border 1px solid #fff
        border-radius 10px
    .active 
        width 12px
        height 12px
        margin 0
        background-color #fff
.carousel-caption 
    position absolute
    right 15%
    bottom 20px
    left 15%
    z-index 10
    padding-top 20px
    padding-bottom 20px
    color #fff
    text-align center
    text-shadow 0 1px 2px rgba(0, 0, 0, .6)
    right 20%
    left 20%
    padding-bottom 30px
    .btn 
        text-shadow none
.carousel-control .glyphicon-chevron-left,
  .carousel-control .icon-prev 
    margin-left -15px
.carousel-control .glyphicon-chevron-right,
  .carousel-control .icon-next 
    margin-right -15px
}
.clearfix:before,
.clearfix:after,
.dl-horizontal dd:before,
.dl-horizontal dd:after,
.container:before,
.container:after,
.container-fluid:before,
.container-fluid:after,
.row:before,
.row:after,
.form-horizontal .form-group:before,
.form-horizontal .form-group:after,
.btn-toolbar:before,
.btn-toolbar:after,
.btn-group-vertical > .btn-group:before,
.btn-group-vertical > .btn-group:after,
.nav:before,
.nav:after,
.navbar:before,
.navbar:after,
.navbar-header:before,
.navbar-header:after,
.navbar-collapse:before,
.navbar-collapse:after,
.pager:before,
.pager:after,
.panel-body:before,
.panel-body:after,
.modal-footer:before,
.modal-footer:after 
    display table
    content " "
.clearfix:after,
.dl-horizontal dd:after,
.container:after,
.container-fluid:after,
.row:after,
.form-horizontal .form-group:after,
.btn-toolbar:after,
.btn-group-vertical > .btn-group:after,
.nav:after,
.navbar:after,
.navbar-header:after,
.navbar-collapse:after,
.pager:after,
.panel-body:after,
.modal-footer:after 
    clear both
.center-block 
    display block
    margin-right auto
    margin-left auto
.pull-left 
    float left !important
.hide 
    display none !important
.show 
    display block !important
.invisible 
    visibility hidden
.text-hide 
    font 0/0 a
    color transparent
    text-shadow none
    background-color transparent
    border 0
.hidden 
    display none !important
    visibility hidden !important
.affix 
    position fixed
    -webkit-transform translate3d(0, 0, 0)
    -o-transform translate3d(0, 0, 0)
    transform translate3d(0, 0, 0)
@-ms-viewport 
    width device-width
.visible-xs,
.visible-sm,
.visible-md,
.visible-lg 
    display none !important
.visible-xs-block,
.visible-xs-inline,
.visible-xs-inline-block,
.visible-sm-block,
.visible-sm-inline,
.visible-sm-inline-block,
.visible-md-block,
.visible-md-inline,
.visible-md-inline-block,
.visible-lg-block,
.visible-lg-inline,
.visible-lg-inline-block 
    display none !important
th.visible-xs,
  td.visible-xs 
    display table-cell !important
th.visible-sm,
  td.visible-sm 
    display table-cell !important
th.visible-md,
  td.visible-md 
    display table-cell !important
th.visible-lg,
  td.visible-lg 
    display table-cell !important
th.visible-print,
  td.visible-print 
    display table-cell !important
