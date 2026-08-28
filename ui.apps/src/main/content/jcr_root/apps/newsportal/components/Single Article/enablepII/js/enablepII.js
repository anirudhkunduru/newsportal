(function (document, $) {
    "use strict";

    function showHide(component, element) {

        var target = $(element).data("cqDialogShowhideTarget");
        var $target = $(target);

        if (!target || !$target.length) {
            return;
        }

        var $fields = $target.find("input, select, textarea, button");
        var $deleteCssClass = $("input[name='./cssClass@Delete']");

        if (component.checked) {

            // Enable CSS Class field
            $fields.prop("disabled", false);
            $deleteCssClass.prop("disabled", true);

        } else {

            // Disable CSS Class field
            $fields.prop("disabled", true);
            $deleteCssClass.prop("disabled", false);

            // Clear the value from dialog
            $fields.val("");
        }
    }


    function checkboxShowHideHandler(el) {

        el.each(function (i, element) {

            if ($(element).is("coral-checkbox")) {

                Coral.commons.ready(element, function (component) {

                    /*
                     * Set initial state
                     */
                    showHide(component, element);


                    /*
                     * Checkbox change event
                     */
                    component.on("change", function () {

                        /*
                         * Enable / disable CSS field
                         */
                        showHide(component, element);


                    });

                });

            }

        });

    }


    /*
     * When dialog is loaded
     */
    $(document).on("foundation-contentloaded", function (e) {

        checkboxShowHideHandler(
            $(e.target).find(".cq-dialog-showhide")
        );

    });


})(document, Granite.$);